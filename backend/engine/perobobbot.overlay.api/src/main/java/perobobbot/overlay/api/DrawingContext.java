package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.rendering.Renderer;

import java.awt.*;

public interface DrawingContext extends AutoCloseable {

    Color TRANSPARENT = new Color(0,0,0,0);

    /**
     * @return the graphics that can be used to draw on the overlay
     */
    @NonNull
    Renderer getGraphics2D();

    /**
     * @return the width available for the draw
     */
    int getWidth();

    /**
     * @return the height available for the draw
     */
    int getHeight();

    /**
     * clear the drawing
     */
    void clearDrawing();

}
