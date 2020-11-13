package perobobbot.rendering;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.rendering.graphics2d.RendererUsingGraphics2D;

import java.awt.*;
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

    @NonNull Renderer translate(double x, double y);

    @NonNull Renderer setColor(@NonNull Color color);

    @NonNull Renderer fillCircle(int xc, int yc, int radius);

    @NonNull Renderer withPrivateContext(@NonNull Consumer1<? super Renderer> drawer);

    @NonNull Renderer drawImage(@NonNull BufferedImage image, int x, int y);

    @NonNull Renderer drawImage(@NonNull BufferedImage image, int x, int y, @NonNull Color bkgColor);

    @NonNull Renderer fillRect(int x, int y, int width, int height);

    @NonNull Renderer drawString(@NonNull String string, double x, double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment);

    @NonNull Renderer setFontSize(float fontSize);

    @NonNull Font getFont();

    @NonNull Renderer setFont(@NonNull Font font);

    double getTextLineHeight();

    @NonNull Rectangle2D getMaxCharBounds();

    default @NonNull Renderer drawString(@NonNull String string, @NonNull double x, @NonNull double y) {
        return drawString(string,x,y,HAlignment.LEFT,VAlignment.TOP);
    }

    default @NonNull Renderer drawString(@NonNull String string, @NonNull double x, @NonNull double y, @NonNull VAlignment vAlignment) {
        return drawString(string,x,y,HAlignment.LEFT,vAlignment);
    }

    @Override
    default void close(){
        dispose();
    }


}
