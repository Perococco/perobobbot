package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.rendering.Renderer;
import perobobbot.timeline.PropertyFactory;

public interface OverlayIteration extends PropertyFactory {

    /**
     * @return the iteration count
     */
    long getIterationCount();

    /**
     * @return the time since the overlay started
     */
    double getTime();

    /**
     * @return the time since the previous iteration
     */
    double getDeltaTime();

    /**
     * @return the renderer to use to draw on the overlay
     */
    @NonNull Renderer getRenderer();

    /**
     * @return the sound context that can be used to play sound on the overlay
     */
    @NonNull SoundContext getSoundContext();

    default void clearOverlay() {
        getRenderer().clearOverlay();
    }
}
