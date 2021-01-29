package perobobbot.rendering.histogram;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;

import java.awt.*;

@RequiredArgsConstructor
public class SimpleBarDrawer implements BarDrawer {

    public static BarDrawer plain(@NonNull Paint paint) {
        return new SimpleBarDrawer((barWidth, barHeight, histogramHeight) -> paint);
    }

    private final PaintComputer paintComputer;

    @Override
    public void drawBar(@NonNull Renderer renderer, double barWidth, double barHeight, double percent) {
        if (barHeight <= 0) {
            return;
        }
        final var paint = paintComputer.compute(barWidth,barHeight,100*barHeight/percent);
        renderer.setPaint(paint);
        renderer.fillRect(0,0,barWidth,barHeight);
    }

}
