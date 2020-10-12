package perobobbot.overlay;

import lombok.NonNull;

import java.awt.*;

public interface DrawingContext extends AutoCloseable {

    Color TRANSPARENT = new Color(0,0,0,0);

    @NonNull
    Graphics2D getGraphics2D();

    int getWidth();

    int getHeight();

    void clearDrawing();

    @NonNull
    default Graphics2D createGraphics2D() {
        return (Graphics2D)getGraphics2D().create();
    }

}
