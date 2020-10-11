package perobobbot.server.config.overlay;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.DisposableBean;
import perobobbot.common.lang.Subscription;
import perobobbot.overlay.FrameRate;
import perobobbot.overlay.Overlay;
import perobobbot.overlay.OverlayClient;
import perobobbot.overlay.OverlayController;

public class OverlayComponent implements Overlay, DisposableBean {

    @NonNull
    public static OverlayComponent create(@NonNull String name) {
        return new OverlayComponent(name,1920,1080);
    }

    @NonNull
    private final OverlayController controller;

    @NonNull
    @Getter
    private final String name;

    @Getter
    private final int width;

    @Getter
    private final int height;

    @NonNull
    @Getter
    private final FrameRate frameRate = FrameRate.FPS_30;

    public OverlayComponent(@NonNull String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.controller = OverlayController.create(name, width, height, frameRate);
        this.controller.start();
    }

    @Override
    public @NonNull Subscription addClient(@NonNull OverlayClient client) {
        return this.controller.addClient(client);
    }

    @Override
    public void destroy() {
        controller.stop();
    }

}
