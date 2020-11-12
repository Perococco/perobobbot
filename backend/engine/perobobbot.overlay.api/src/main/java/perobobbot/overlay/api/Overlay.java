package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.common.sound.SoundRegistry;
import perobobbot.lang.Subscription;
import perobobbot.rendering.Size;

public interface Overlay extends SoundRegistry {

    /**
     * Add a client to this overlay
     * @param client the client to add
     * @return a subscription that can be used to remove the client from this overlay
     */
    @NonNull
    Subscription addClient(@NonNull OverlayClient client);

    /**
     * @return the size of the overlay
     */
    Size getOverlaySize();

    /**
     * @return the frame rate at which the overlay is redrawed
     */
    @NonNull
    FrameRate getFrameRate();

}
