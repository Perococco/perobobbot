package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.common.lang.Subscription;
import perobobbot.common.sound.SoundRegistry;

public interface Overlay extends SoundRegistry {

    /**
     * Add a client to this overlay
     * @param client the client to add
     * @return a subscription that can be used to remove the client from this overlay
     */
    @NonNull
    Subscription addClient(@NonNull OverlayClient client);

    /**
     * @return the width of the overlay
     */
    int getWidth();

    /**
     * @return the height of the overlay
     */
    int getHeight();

    /**
     * @return the frame rate at which the overlay is redrawed
     */
    @NonNull
    FrameRate getFrameRate();

}
