package perobobbot.overlay.api;

import lombok.NonNull;

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

    @NonNull OverlayRenderer getOverlayRenderer();

    @NonNull SoundContext getSoundContext();

    @Override
    default void close() throws Exception {
        getOverlayRenderer().dispose();
    }

    default void clearOverlay() {
        getOverlayRenderer().clearOverlay();
    }
}
