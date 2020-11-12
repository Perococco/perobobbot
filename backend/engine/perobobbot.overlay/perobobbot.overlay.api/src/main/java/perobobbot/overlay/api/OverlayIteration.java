package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.rendering.Renderer;

public interface OverlayIteration extends AutoCloseable {

    /**
     * @return the iteration count
     */
    long getIterationCount();

    /**
     * @return the time since the overlay started
     */
    double getTime();

    /**
     * @return the time since ne previous iteration
     */
    double getDeltaTime();

    @NonNull Renderer getRenderer();

    @NonNull SoundContext getSoundContext();

    @Override
    default void close() {
        getRenderer().dispose();
    }

    default void clearOverlay() {
        getRenderer().clearOverlay();
    }
}
