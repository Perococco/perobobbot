package perococco.perobobbot.common.sound;

import lombok.NonNull;
import perobobbot.common.sound.Sound;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class InMemorySound implements Sound {

    private final float[][] data;

    private final int length;

    private int offset = 0;

    public InMemorySound(float[][] data) {
        this.data = data;
        this.length = Arrays.stream(data).mapToInt(f -> f.length).min().orElse(0);
    }

    @Override
    public void copy(@NonNull ByteBuffer buffer, int nbSamples) {
        if (offset>=length) {
            return;
        }
        final int copied = Math.min(offset+nbSamples,length)-offset;
        for (int channelIdx = 0; channelIdx < 2; channelIdx++) {
            for (int i = 0,j=offset; i < copied; i++,j++) {
                buffer.putFloat(data[channelIdx][j]);
            }
            for (int i = copied; i < nbSamples ; i++) {
                buffer.putFloat(0.0f);
            }
        }
        offset+=copied;
    }

    @Override
    public void copy(@NonNull float[][] buffer, int nbSamples) {
        if (offset>=length) {
            return;
        }
        final int copied = Math.min(offset+nbSamples,length)-offset;
        for (int channelIdx = 0; channelIdx < 2; channelIdx++) {
            System.arraycopy(buffer[channelIdx],0,data[channelIdx],offset,copied);
            if (copied<nbSamples) {
                Arrays.fill(buffer[channelIdx],copied,nbSamples,0.0f);
            }
        }
        offset+=copied;
    }

    @Override
    public void close() {}

    @Override
    public boolean isCompleted() {
        return offset>=length;
    }
}
