package perobobbot.common.sound;

import lombok.NonNull;

import java.nio.ByteBuffer;

public interface Sound {

    void copy(@NonNull ByteBuffer buffer, int nbSamples);
    void copy(@NonNull float[][] buffer, int nbSamples);

    void close();

    boolean isCompleted();

}
