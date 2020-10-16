package perococco.perobobbot.common.sound;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.AudioStreamUtils;
import perobobbot.common.sound.NDIAudioFormat;
import perobobbot.common.sound.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InMemorySoundResource implements SoundResource {

    @NonNull
    public static SoundResource create(@NonNull URL url,
                                       @NonNull NDIAudioFormat audioFormat) throws IOException, UnsupportedAudioFileException {
        final AudioInputStream stream = AudioSystem.getAudioInputStream(audioFormat, AudioSystem.getAudioInputStream(url));
        final byte[] bytes = AudioStreamUtils.readAllBytes(stream);
        final int nbChannels = audioFormat.getChannels();
        final int nbBytesPerChannel = bytes.length / (nbChannels*Float.BYTES);
        final float[][] data = new float[nbChannels][nbBytesPerChannel];

        final FloatBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();

        int tgtPos = 0;
        while (buffer.hasRemaining()) {
            data[0][tgtPos]=buffer.get();
            data[1][tgtPos]=buffer.get();
            tgtPos++;
        }
        return new InMemorySoundResource(data);

    }

    private final float[][] data;

    @Override
    public @NonNull Sound createSound() {
        return new InMemorySound(data);
    }
}
