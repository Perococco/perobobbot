package perobobbot.overlay.newtek;

import com.walker.devolay.DevolayAudioFrame;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@RequiredArgsConstructor
@Getter
public class AudioFrame {

    @NonNull
    private final ByteBuffer data;

    @NonNull
    private final DevolayAudioFrame frame;
}
