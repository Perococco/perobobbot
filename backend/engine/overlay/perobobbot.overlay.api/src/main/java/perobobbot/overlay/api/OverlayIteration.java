package perobobbot.overlay.api;

public interface OverlayIteration extends DrawingContext, SoundContext {

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

}
