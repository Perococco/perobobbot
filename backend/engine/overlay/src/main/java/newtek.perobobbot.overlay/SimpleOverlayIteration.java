package newtek.perobobbot.overlay;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.OverlayIteration;

import java.awt.*;

@RequiredArgsConstructor
@Builder
public class SimpleOverlayIteration implements OverlayIteration {

    @Getter
    private final double time;

    @Getter
    private final double deltaTime;

    @Getter
    private final long iterationCount;

    @NonNull
    private final SimpleDrawingContext drawingContext;

    @Override
    @NonNull
    public Graphics2D getGraphics2D() {
        return drawingContext.getGraphics2D();
    }

    @Override
    public int getWidth() {
        return drawingContext.getWidth();
    }

    public int getHeight() {
        return drawingContext.getHeight();
    }

    public void close() {
        drawingContext.close();
    }

    @Override
    public void clearDrawing() {
        drawingContext.clear();
    }
}
