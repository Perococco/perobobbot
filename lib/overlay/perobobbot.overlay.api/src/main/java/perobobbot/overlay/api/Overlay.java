package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.lang.Subscription;
import perobobbot.rendering.ScreenSize;
import perobobbot.sound.SoundRegistry;
import perobobbot.timeline.PropertyFactory;

/**
 * WARNING!! Overlay is shared amongst all users. A user specific overlay might be needed
 */
public interface Overlay extends SoundRegistry, PropertyFactory {

    int VERSION = 1;

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
    ScreenSize getOverlaySize();

    /**
     * @return the frame rate at which the overlay is redrawed
     */
    @NonNull
    FrameRate getFrameRate();

}
