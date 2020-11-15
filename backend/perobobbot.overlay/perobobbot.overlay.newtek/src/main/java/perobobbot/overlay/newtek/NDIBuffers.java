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


    public void clear() {
        nbAudioSamples = 0;
        video.rewind();
        while(video.hasRemaining()) {
            video.putLong(0);
        }
    }
}
