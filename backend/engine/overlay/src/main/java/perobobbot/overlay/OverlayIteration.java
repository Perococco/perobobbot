package perobobbot.overlay;

public interface OverlayIteration extends DrawingContext {

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
