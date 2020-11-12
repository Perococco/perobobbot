package perobobbot.common.sound;

import lombok.NonNull;

import java.nio.ByteBuffer;

public interface Sound {

    void copyTo(@NonNull ByteBuffer buffer, int nbSamples);
    void copyTo(@NonNull float[][] buffer, int nbSamples);
    void addTo(@NonNull float[][] buffer, int nbSamples);

    void close();

    boolean isCompleted();

}
