package perobobbot.overlay.newtek;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@RequiredArgsConstructor
public class NDIBuffers {

    @Getter
    int nbAudioSamples = -1;

    @NonNull
    final float[][] audio;

    @NonNull
    final ByteBuffer video;


}
