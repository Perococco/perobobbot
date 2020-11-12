package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.overlay.api._private.OverlayRendererUsingGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public interface OverlayRenderer extends AutoCloseable {

    static @NonNull OverlayRenderer withGraphics2D(@NonNull Graphics2D graphics2D, @NonNull OverlaySize overlaySize) {
        return new OverlayRendererUsingGraphics2D(graphics2D, overlaySize);
    }

    Color TRANSPARENT = new Color(0,0,0,0);

    @NonNull OverlaySize getOverlaySize();

    void dispose();

    @NonNull Color getBackground();

    @NonNull OverlayRenderer setBackground(@NonNull Color transparent);

    @NonNull OverlayRenderer clearOverlay();

    @NonNull OverlayRenderer translate(double x, double y);

    @NonNull OverlayRenderer setColor(@NonNull Color color);

    @NonNull OverlayRenderer fillCircle(int xc, int yc, int radius);

    @NonNull OverlayRenderer withPrivateContext(Consumer1<? super OverlayRenderer> drawer);

    @NonNull OverlayRenderer drawImage(@NonNull BufferedImage image, int x, int y);

    @NonNull OverlayRenderer drawImage(@NonNull BufferedImage image, int x, int y, @NonNull Color bkgColor);

    @NonNull OverlayRenderer fillRect(int x, int y, int width, int height);

    @NonNull OverlayRenderer drawString(@NonNull String string, @NonNull double x, @NonNull double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment);

    @NonNull OverlayRenderer setFontSize(float fontSize);

    @NonNull Font getFont();

    @NonNull OverlayRenderer setFont(@NonNull Font font);

    double getTextLineHeight();

    @NonNull Rectangle2D getMaxCharBounds();

    default @NonNull OverlayRenderer drawString(@NonNull String string, @NonNull double x, @NonNull double y) {
        return drawString(string,x,y,HAlignment.LEFT,VAlignment.TOP);
    }

    default @NonNull OverlayRenderer drawString(@NonNull String string, @NonNull double x, @NonNull double y, @NonNull VAlignment vAlignment) {
        return drawString(string,x,y,HAlignment.LEFT,vAlignment);
    }

    @Override
    default void close(){
        dispose();
    }


}
