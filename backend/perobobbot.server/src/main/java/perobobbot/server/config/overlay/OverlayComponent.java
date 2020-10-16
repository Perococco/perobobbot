package perobobbot.server.config.overlay;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import perobobbot.common.lang.Subscription;
import perobobbot.overlay.FrameRate;
import perobobbot.overlay.Overlay;
import perobobbot.overlay.OverlayClient;
import perobobbot.overlay.OverlayController;

@RequiredArgsConstructor
public class OverlayComponent implements Overlay, DisposableBean {

    @NonNull
    private final OverlayController controller;

    @Override
    public @NonNull Subscription addClient(@NonNull OverlayClient client) {
        return this.controller.addClient(client);
    }

    @Override
    public int getWidth() {
        return controller.getWidth();
    }

    @Override
    public int getHeight() {
        return controller.getHeight();
    }

    @Override
    public @NonNull FrameRate getFrameRate() {
        return controller.getFrameRate();
    }

    @Override
    public void destroy() {
        controller.stop();
    }

}
