package newtek.perobobbot.overlay;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class SimpleDrawingContext implements AutoCloseable {

    public static final Color TRANSPARENT = new Color(0,0,0,0);

    @NonNull
    @Getter
    private final Graphics2D graphics2D;

    @Getter
    private final int width;

    @Getter
    private final int height;

    @Override
    public void close() {
        graphics2D.dispose();
    }

    public void clear() {
        final Color paint = graphics2D.getBackground();
        graphics2D.setBackground(TRANSPARENT);
        graphics2D.clearRect(0,0,width,height);
        graphics2D.setBackground(paint);
    }

}
