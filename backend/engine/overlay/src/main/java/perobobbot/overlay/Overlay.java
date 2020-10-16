package perobobbot.overlay;

import lombok.NonNull;
import perobobbot.common.lang.Subscription;
import perobobbot.common.sound.SoundRegistry;

public interface Overlay extends SoundRegistry {

    @NonNull
    Subscription addClient(@NonNull OverlayClient client);

    int getWidth();

    int getHeight();

    @NonNull
    FrameRate getFrameRate();

}
