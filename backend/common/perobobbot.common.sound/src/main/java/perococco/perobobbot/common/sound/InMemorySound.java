package perococco.perobobbot.common.sound;

import lombok.NonNull;
import perobobbot.common.sound.Sound;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class InMemorySound implements Sound {

    private final float[][] data;

    private final int length;

    private int offset = 0;

    private boolean closed;

    public InMemorySound(float[][] data) {
        this.data = data;
        this.length = Arrays.stream(data).mapToInt(f -> f.length).min().orElse(0);
    }

    @Override
    public void copyTo(@NonNull ByteBuffer buffer, int nbSamples) {
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

    private int numberToProcess(int nbRequested) {
        if (offset>=length) {
            return 0;
        }
        return Math.min(offset+nbRequested,length)-offset;
    }

    @Override
    public void copyTo(@NonNull float[][] buffer, int nbSamples) {
        final int nbToCopy = numberToProcess(nbSamples);
        for (int channelIdx = 0; channelIdx < 2; channelIdx++) {
            final float[] target = buffer[channelIdx];
            final float[] source = data[channelIdx];

            System.arraycopy(source,offset,target,0,nbToCopy);
            if (nbToCopy<nbSamples) {
                Arrays.fill(target,nbToCopy,nbSamples,0.0f);
            }
        }
        offset+=nbToCopy;
    }

    @Override
    public void addTo(@NonNull float[][] buffer, int nbSamples) {
        final int nbAdded = numberToProcess(nbSamples);
        for (int channelIdx = 0; channelIdx < 2; channelIdx++) {
            final float[] target = buffer[channelIdx];
            final float[] source = data[channelIdx];

            for (int i = 0,j=offset; i < nbAdded; i++,j++) {
                target[i]+=source[j];
            }
        }
        offset+=nbAdded;
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public boolean isCompleted() {
        return closed || offset>=length;
    }
}
