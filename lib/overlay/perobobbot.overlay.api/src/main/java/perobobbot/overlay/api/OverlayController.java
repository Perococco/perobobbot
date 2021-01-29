package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.overlay.api._private.OverlayControllerFactory;
import perobobbot.rendering.ScreenSize;
import perobobbot.sound.SoundManager;

/**
 * An overlay with extra method to control its lifecycle
 */
public interface OverlayController extends Overlay {

    interface Factory {

        boolean isDefault();

        @NonNull String getImplementationName();

        @NonNull OverlayController create(@NonNull String name, @NonNull ScreenSize size, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager);
    }

    @NonNull
    static OverlayController create(@NonNull String name, @NonNull ScreenSize size, @NonNull SoundManager soundManager) {
        return create(name,size,FrameRate.FPS_30,soundManager);
    }

    static OverlayController create(@NonNull String name, @NonNull ScreenSize size, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return new OverlayControllerFactory().create(name, size, frameRate, soundManager);
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
