package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.api._private.OverlayControllerFactory;

/**
 * An overlay with extra method to control its lifecycle
 */
public interface OverlayController extends Overlay {

    interface Factory {

        boolean isDefault();

        @NonNull String getImplementationName();

        @NonNull OverlayController create(@NonNull String name, int width, int height, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager);
    }

    @NonNull
    static OverlayController create(@NonNull String name, int width, int height, @NonNull SoundManager soundManager) {
        return create(name,width,height,FrameRate.FPS_30,soundManager);
    }

    static OverlayController create(@NonNull String name, int width, int height, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return new OverlayControllerFactory().create(name, width, height, frameRate, soundManager);
    }

    /**
     * @return the name of this controller
     */
    @NonNull String getImplementationName();

    /**
     * Start this overlay
     */
    void start();

    /**
     * Stop this overlay
     */
    void stop();

}
