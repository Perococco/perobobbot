package perococco.perobobbot.common.sound;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.AudioStreamUtils;
import perobobbot.common.sound.NDIAudioFormat;
import perobobbot.common.sound.Sound;

import javax.sound.sampled.*;
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
        final int nbSamples = audioFormat.computeOneChannelSize(bytes.length);
        final float[][] data = new float[2][nbSamples];

        final FloatBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();

        for (int idx = 0; idx < nbSamples; idx++) {
            for (int channelIdx = 0; channelIdx < 2; channelIdx++) {
                data[channelIdx][idx] = buffer.get();
            }
        }

        return new InMemorySoundResource(data);
    }

    private final float[][] data;

    @Override
    public @NonNull Sound createSound() {
        throw new RuntimeException("Not Yet Implemented");
    }
}
