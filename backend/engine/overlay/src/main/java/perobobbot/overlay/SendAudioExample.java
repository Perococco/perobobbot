package perobobbot.overlay;

import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayAudioFrame;
import com.walker.devolay.DevolaySender;
import lombok.NonNull;
import perobobbot.common.sound.Sound;
import perobobbot.common.sound.SoundManager;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
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

//    public static void conversion() throws IOException, UnsupportedAudioFileException {
//        final Path audioFile = Path.of("/home/perococco/Documents/foghorn-daniel_simon.wav");
//        final AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(audioFile.toFile());
//        final AudioInputStream stream1 = AudioSystem.getAudioInputStream(audioFile.toFile());
//        System.out.println(stream1.getFormat());
//        final AudioFormat target = new AudioFormat(
//                AudioFormat.Encoding.PCM_FLOAT,
//                48000.f,
//                32,
//                2,
//                )
//
//    }
//
//    public static void compare() throws IOException, UnsupportedAudioFileException {
//        final Path audioFile32 = Path.of("/home/perococco/Documents/foghorn-daniel_simon_32.wav");
//        final Path audioFile24 = Path.of("/home/perococco/Documents/foghorn-daniel_simon_24.wav");
//        final AudioInputStream stream32 = AudioSystem.getAudioInputStream(audioFile32.toFile());
//        final AudioInputStream stream24 = AudioSystem.getAudioInputStream(audioFile24.toFile());
//
//        byte[] b32 = new byte[4];
//        byte[] b24 = new byte[4];
//
//        int nb = b32.length / 4;
//        for (int i = 0; i < nb; i++) {
//            stream32.read(b32);
//            stream24.read(b24, 1, 3);
//            if (i == 2729) {
//                displayBytes("32 - ", 4, u -> b32[u]);
//                displayBytes("24 - ", 4, u -> b24[u]);
//            }
//            for (int j = 0; j < 4; j++) {
//                if (b32[j] != b24[j]) {
//                    throw new RuntimeException(String.format("dif at %08x - %08x - ", i * 4 + 0x2d, i * 3 + 0x2d));
//                }
//            }
//        }
//
//    }

    private static void launchTest() throws IOException, UnsupportedAudioFileException, InterruptedException {
        final SoundManager soundManager = SoundManager.create(44100);
        final SoundPlayer run = new SoundPlayer(soundManager);
        final Thread t = new Thread(run);
        t.start();

        final Path audioFile = Path.of("/home/perococco/Documents/foghorn-daniel_simon.wav");
        final UUID soundId = soundManager.registerSoundResource(audioFile.toUri().toURL());

        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String line = scanner.nextLine();
            if (line.equals("stop")) {
                System.out.println("QUIT!!");
                t.interrupt();
                break;
            } else if (line.equals("play")) {
                run.playSound = soundId;
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

        private final SoundManager soundManager;

        final int sampleCount = 1024;

        private final DevolaySender sender;
        private final Frame[] frames;
        private int idx = 0;

        public SoundPlayer(@NonNull SoundManager soundManager) throws IOException, UnsupportedAudioFileException {
            Devolay.loadLibraries();
            this.soundManager = soundManager;
            this.sender = new DevolaySender("Overlay", null, false, true);

            frames = new Frame[]{
                    new Frame(soundManager.getSampleRate(),sampleCount),
                    new Frame(soundManager.getSampleRate(),sampleCount)};
        }

        public UUID playSound = null;
        public Sound sound = null;

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (playSound != null && sound == null) {
                    sound = soundManager.createSound(playSound).orElse(null);
                    playSound = null;
                }

                final Frame frame = frames[idx];
                final ByteBuffer data = frame.data;
                data.position(0);
                if (sound != null) {
                    sound.copy(data, sampleCount);
                    if (sound.isCompleted()) {
                        sound.close();
                        System.out.println("STOP");
                        sound = null;
                    }
                } else {
                    clearData(data);
                }
                data.flip();
                sender.sendAudioFrame(frame.audioFrame);
                idx = 1-idx;
            }
            Arrays.stream(frames).forEach(f -> f.audioFrame.close());
            sender.close();
        }

        private void clearData(ByteBuffer data) {
            for (int i = 0; i < sampleCount; i++) {
                data.putFloat(0f);
                data.putFloat(0f);
            }
        }

    }

    private static class Frame {

        private final ByteBuffer data;

        private final DevolayAudioFrame audioFrame;

        public Frame(int sampleRate, int sampleCount) {
            this.data = ByteBuffer.allocateDirect(sampleCount * Float.BYTES * 2)
                                  .order(ByteOrder.LITTLE_ENDIAN);

            audioFrame = new DevolayAudioFrame();
            audioFrame.setSampleRate(sampleRate);
            audioFrame.setChannels(2);
            audioFrame.setSamples(sampleCount);
            audioFrame.setData(data);
            audioFrame.setChannelStride(sampleCount * Float.BYTES);

        }
    }


}
