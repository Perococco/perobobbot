package perobobbot.rendering;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.physics.ROVector2D;
import perobobbot.rendering.graphics2d.RendererUsingGraphics2D;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public interface Renderer extends AutoCloseable {

    static @NonNull Renderer withGraphics2D(@NonNull Graphics2D graphics2D, @NonNull Size renderingSize) {
        return new RendererUsingGraphics2D(graphics2D, renderingSize);
    }

    Color TRANSPARENT = new Color(0,0,0,0);

    @NonNull Size getDrawingSize();

    void dispose();

    @NonNull Color getBackground();

    @NonNull Renderer setBackground(@NonNull Color transparent);

    @NonNull Renderer clearOverlay();

    default @NonNull Renderer translate(@NonNull ROVector2D position) {
        return translate(position.getX(),position.getY());
    }

    @NonNull Renderer translate(double x, double y);

    @NonNull Renderer setColor(@NonNull Color color);

    @NonNull Renderer fillCircle(int xc, int yc, int radius);

    @NonNull Renderer withPrivateContext(@NonNull Consumer1<? super Renderer> drawer);

    @NonNull Renderer withPrivateTransform(@NonNull Consumer1<? super Renderer> drawer);

    @NonNull Renderer drawImage(@NonNull BufferedImage image, int x, int y);

    @NonNull Renderer drawImage(@NonNull BufferedImage image, int x, int y, @NonNull Color bkgColor);

    @NonNull Renderer fillRect(int x, int y, int width, int height);

    default @NonNull Renderer fillRect(int x, int y, Size size) {
        return fillRect(x,y,size.getWidth(),size.getHeight());
    }

    @NonNull Renderer drawString(@NonNull String string, double x, double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment);

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

    void rotate(double angle);

    void rotate(double angle, double xc, double yc);
}
