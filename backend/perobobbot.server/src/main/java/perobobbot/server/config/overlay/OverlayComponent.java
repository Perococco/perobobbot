package perobobbot.server.config.overlay;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.DisposableBean;
import perobobbot.overlay.Overlay;
import perobobbot.overlay.OverlayController;

@RequiredArgsConstructor
public class OverlayComponent implements Overlay, DisposableBean {

    @NonNull
    @Delegate(types = {Overlay.class})
    private final OverlayController controller;

    @Override
    public void destroy() {
        controller.stop();
    }

}
