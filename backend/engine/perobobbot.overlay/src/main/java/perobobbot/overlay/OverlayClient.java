package perobobbot.overlay;

import lombok.NonNull;

public interface OverlayClient {

    /**
     * Call when the client is added to the overlay
     * to perform initialization
     * @param overlay the overlay this client is added to
     */
    default void initialize(@NonNull Overlay overlay) {};

    /**
     * @param overlay the overlay this client was added to
     */
    default void dispose(@NonNull Overlay overlay) {};

    /**
     * Call when this client must render
     * @param iteration the iteration information
     */
    void render(@NonNull OverlayIteration iteration);
}
