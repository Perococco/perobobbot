package perobobbot.rendering;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.physics.ROVector2D;
import perobobbot.physics.Vector2D;
import perobobbot.rendering.graphics2d.RendererUsingGraphics2D;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public interface Renderer extends AutoCloseable {

    static @NonNull Renderer withGraphics2D(@NonNull Graphics2D graphics2D, @NonNull ScreenSize renderingSize) {
        return new RendererUsingGraphics2D(graphics2D, renderingSize);
    }

    Color TRANSPARENT = new Color(0,0,0,0);

    @NonNull ScreenSize getDrawingSize();

    void dispose();

    @NonNull Color getBackground();

    @NonNull Renderer setBackground(@NonNull Color transparent);

    @NonNull Renderer setTextAntialiasing(boolean enable);

    @NonNull Renderer clearOverlay();

    default @NonNull Renderer translate(@NonNull ROVector2D position) {
        return translate(position.getX(),position.getY());
    }

    @NonNull Renderer translate(double x, double y);

    @NonNull Renderer setColor(@NonNull Color color);

    @NonNull Renderer setPaint(@NonNull Paint paint);

    @NonNull Renderer fillCircle(double xc, double yc, double radius);

    @NonNull Renderer withPrivateContext(@NonNull Consumer1<? super Renderer> drawer);

    @NonNull Renderer withPrivateTransform(@NonNull Consumer1<? super Renderer> drawer);

    @NonNull Renderer setStroke(@NonNull Stroke stoke);

    @NonNull Renderer drawLine(double x0, double y0, double x1, double y1);

    default @NonNull Renderer drawLine(@NonNull Vector2D start, @NonNull Vector2D end) {
        return drawLine(start.getX(),start.getY(),end.getX(),end.getY());
    }

    @NonNull Renderer drawImage(@NonNull BufferedImage image, double x, double y);

    @NonNull Renderer drawImage(@NonNull BufferedImage image, double x, double y, @NonNull Color bkgColor);

    @NonNull Renderer fillRect(double x, double y, double width, double height);

    default @NonNull Renderer fillRect(double x, double y, ScreenSize size) {
        return fillRect(x,y,size.getWidth(),size.getHeight());
    }

    @NonNull Renderer drawString(@NonNull String string, double x, double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment);

    default @NonNull Renderer drawString(@NonNull String string, Vector2D position, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment) {
        return drawString(string,position.getX(),position.getY(),hAlignment,vAlignment);
    }

    @NonNull Renderer setFontSize(float fontSize);

    @NonNull Font getFont();

    @NonNull Renderer setFont(@NonNull Font font);

    double getTextLineHeight();

    @NonNull BlockBuilder blockBuilder();

    /**
     * Returns the bounds for the character with the maximum bounds
     * in the specified {@code Graphics} context.
     * @return a {@code Rectangle2D} that is the
     * bounding box for the character with the maximum bounds.
     * @see java.awt.Font#getMaxCharBounds(FontRenderContext)
     */
    @NonNull Rectangle2D getMaxCharBounds();

    default @NonNull Renderer drawString(@NonNull String string, double x, double y) {
        return drawString(string,x,y,HAlignment.LEFT,VAlignment.TOP);
    }

    default @NonNull Renderer drawString(@NonNull String string, double x, double y, @NonNull VAlignment vAlignment) {
        return drawString(string,x,y,HAlignment.LEFT,vAlignment);
    }

    @Override
    default void close(){
        dispose();
    }


    float getFontSize();

    @NonNull Color getColor();

    default void scale(double scale) {
        scale(scale,scale);
    }

    void scale(double sx, double sy);

    void rotate(double angle);

    void rotate(double angle, double xc, double yc);

}
