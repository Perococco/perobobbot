package perobobbot.overlay;

import lombok.NonNull;
import perobobbot.common.lang.Subscription;

public interface Overlay {

    @NonNull
    Subscription addClient(@NonNull OverlayClient client);

    int getWidth();

    int getHeight();

    @NonNull
    FrameRate getFrameRate();

}
