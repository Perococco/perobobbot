package perobobbot.overlay.newtek;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.OverlayRenderer;

@RequiredArgsConstructor
public class SimpleDrawingContext implements AutoCloseable {

    @NonNull
    @Getter
    private final OverlayRenderer graphics2D;

    @Getter
    private final int width;

    @Getter
    private final int height;

    @Override
    public void close() {
        graphics2D.dispose();
    }

    public void clear() {
        graphics2D.clearOverlay();
    }

}
