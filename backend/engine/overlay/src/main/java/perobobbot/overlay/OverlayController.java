package perobobbot.overlay;

import lombok.NonNull;
import newtek.perobobbot.overlay.PerococcoOverlayController;
import perobobbot.common.lang.Subscription;

public interface OverlayController {

    @NonNull
    static OverlayController create(@NonNull String name, int width, int height) {
        return create(name,width,height,FrameRate.FPS_30);
    }

    static OverlayController create(@NonNull String name, int width, int height, @NonNull FrameRate frameRate) {
        return new PerococcoOverlayController(name, width, height, frameRate);
    }

    void start();

    void stop();

    @NonNull
    Subscription addClient(@NonNull OverlayClient drawer);

}
