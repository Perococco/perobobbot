package perobobbot.rendering.histogram;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;

import java.awt.*;

@RequiredArgsConstructor
public class PlainBarDrawer implements BarDrawer {

    private final @NonNull Paint paint;

    @Override
    public void drawBar(@NonNull Renderer renderer, int barWidth, int barHeight, double percent) {
        renderer.setPaint(paint);
        renderer.fillRect(0,0,barWidth,barHeight);
    }
}
