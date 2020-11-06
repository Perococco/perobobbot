package perobobbot.overlay;

import lombok.NonNull;
import newtek.perobobbot.overlay.PerococcoOverlayController;
import perobobbot.common.sound.SoundManager;


public interface OverlayController extends Overlay {

    @NonNull
    static OverlayController create(@NonNull String name, int width, int height, @NonNull SoundManager soundManager) {
        return create(name,width,height,FrameRate.FPS_30,soundManager);
    }

    static OverlayController create(@NonNull String name, int width, int height, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return new PerococcoOverlayController(name, width, height, frameRate, soundManager);
    }

    void start();

    void stop();

}
