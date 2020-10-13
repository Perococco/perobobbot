package perobobbot.overlay;

import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayAudioFrameInterleaved32s;
import com.walker.devolay.DevolaySender;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Adapted from NDIlib_Send_Audio.cpp
 */
public class SendAudioExample {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, InterruptedException {
        launchTest();
    }

    public static void compare() throws IOException, UnsupportedAudioFileException {
        final Path audioFile32 = Path.of("/home/perococco/Documents/foghorn-daniel_simon_32.wav");
        final Path audioFile24 = Path.of("/home/perococco/Documents/foghorn-daniel_simon_24.wav");
        final AudioInputStream stream32 = AudioSystem.getAudioInputStream(audioFile32.toFile());
        final AudioInputStream stream24 = AudioSystem.getAudioInputStream(audioFile24.toFile());

        byte[] b32 = new byte[4];
        byte[] b24 = new byte[4];

        int nb = b32.length / 4;
        for (int i = 0; i < nb; i++) {
            stream32.read(b32);
            stream24.read(b24, 1, 3);
            if (i == 2729) {
                displayBytes("32 - ", 4, u -> b32[u]);
                displayBytes("24 - ", 4, u -> b24[u]);
            }
            for (int j = 0; j < 4; j++) {
                if (b32[j] != b24[j]) {
                    throw new RuntimeException(String.format("dif at %08x - %08x - ", i * 4 + 0x2d, i * 3 + 0x2d));
                }
            }
        }

    }

    private static void launchTest() throws IOException, UnsupportedAudioFileException, InterruptedException {
        final SoundPlayer run = new SoundPlayer();
        final Thread t = new Thread(run);
        t.start();

        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String line = scanner.nextLine();
            if (line.equals("stop")) {
                System.out.println("QUIT!!");
                t.interrupt();
                break;
            } else if (line.equals("play")) {
                run.playSound = true;
            }
        }
        t.join();
    }

    private static void displayBytes(String head, int size, IntUnaryOperator op) {
        final String msg = IntStream.range(0, size).map(op)
                                    .map(i -> i & 0xff)
                                    .mapToObj(i -> String.format("0x%02X", i))
                                    .collect(Collectors.joining(" "));
        System.out.println(head + " " + msg);
    }


    private static class SoundPlayer implements Runnable {

        private final DevolaySender sender;
        private final ByteBuffer data;
        private final int sampleRate = 48000;
        private final int channelCount = 2;
        private final int frameCount = 1200;
        private final byte[] sound;
        private final DevolayAudioFrameInterleaved32s audioFrame;
        private final AudioFormat format;

        public SoundPlayer() throws IOException, UnsupportedAudioFileException {
            final Path audioFile = Path.of("/home/perococco/Documents/foghorn-daniel_simon.wav");
            final AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(audioFile.toFile());
            final AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile.toFile());
            this.format = stream.getFormat();
            this.sound = new byte[fileFormat.getByteLength()];
            stream.read(sound);

            Devolay.loadLibraries();
            this.sender = new DevolaySender("Overlay", null, false, true);

            this.data = ByteBuffer.allocateDirect(frameCount * 8);

            this.audioFrame = new DevolayAudioFrameInterleaved32s();
            audioFrame.setSampleRate(sampleRate);
            audioFrame.setChannels(channelCount);
            audioFrame.setSamples(frameCount);
            audioFrame.setData(data);

            System.out.println(format);
        }

        public boolean playSound = false;
        public int offset = 0;

        public void run() {
            boolean display;
            while (!Thread.currentThread().isInterrupted()) {
                display = false;
                data.position(0);
                if (playSound) {
                    if (offset == frameCount * format.getFrameSize() * 100) {
                        display = true;
                        System.out.println("PLAY");
                    }
                    if (format.getFrameSize() == 8) {
                        if (display) {
                            displayBytes("IN ", 32, i -> sound[i + offset]);
                        }
                        copySoundData8();
                    } else if (format.getFrameSize() == 6) {
                        if (display) {
                            displayBytes("IN ", 24, i -> sound[i + offset]);
                        }
                        copySoundData6();
                    } else {
                        System.out.println("STOP");
                        offset = 0;
                        playSound = false;
                        clearData();
                    }
                } else {
                    clearData();
                }
                data.flip();
                if (display) {
                    displayBytes("OUT", 32, data::get);
                }
                sender.sendAudioFrameInterleaved32s(audioFrame);
            }

            audioFrame.close();
            sender.close();
        }

        private void clearData() {
            for (int i = 0; i < frameCount; i++) {
                data.putInt(0);
                data.putInt(0);
            }
        }

        public void copySoundData6() {
            int nbFrameToCopy = Math.min(frameCount, (sound.length - offset) / 6);
            for (int i = 0; i < nbFrameToCopy; i++) {
                for (int j = 0; j < 2; j++) {
                    data.put((byte) 0);
                    data.put(sound[offset]);
                    data.put(sound[offset + 1]);
                    data.put(sound[offset + 2]);
                    offset += 3;
                }
            }
            if (nbFrameToCopy <= 0) {
                playSound = false;
                offset = 0;
            }
            for (int k = 0; k < (frameCount - nbFrameToCopy); k++) {
                data.putInt(0);
                data.putInt(0);
            }
        }

        private int getValue() {
            final int lsb = (((int) sound[offset + 0]) & 0xff);
            final int msb = (((int) sound[offset + 1]) & 0xff) << 8;
            final int hsb = (((int) sound[offset + 2]) & 0xff) << 16;

            int value = hsb | msb | lsb;
            if ((hsb & 0x00800000) != 0) {
                value = -(0x01_000000 - value);
            }
            System.out.println(value);
            return value;
        }

        public void copySoundData8() {
            int nbFrameToCopy = Math.min(frameCount, (sound.length - offset) / 8);
            if (nbFrameToCopy > 0) {
                data.put(sound, offset, nbFrameToCopy * 8);
                offset += nbFrameToCopy * 8;
            } else {
                playSound = false;
                offset = 0;
            }
            for (int k = 0; k < (frameCount - nbFrameToCopy); k++) {
                data.putInt(0);
                data.putInt(0);
            }
        }

    }


    private static void putOneFrame(ByteBuffer data, byte[] sound, int offset) {
        data.putInt(getValue(sound[offset], sound[offset + 1], sound[offset + 2]));
//        data.put((byte)0);
//        data.put(sound,offset+2,1);
//        data.put(sound,offset+1,1);
//        data.put(sound,offset,1);
//        if ((sound[offset+2]&0x80) != 0) {
//            data.put((byte)0xff);
//        } else {
//        data.put((byte)0);
//        }
    }

    public static int getValue(byte b1, byte b2, byte b3) {
        int r = 0;
        r |= (b3 << 24) & 0XFF000000;
        r |= ((b2 & 0xff) << 16);
        r |= ((b1 & 0xff) << 8);
        return r;
    }

    public static double freq(int note, int octave) {
        return 440 * Math.pow(2, octave + (note - 10) / 12.);
    }


}
