package perobobbot.overlay.api;

import lombok.NonNull;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.overlay.api._private.OverlayRendererUsingGraphics2D;

import java.awt.*;
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

    @NonNull OverlayRenderer fillRect(int i, int i1, int logoWidth, int logoHeight);

    @Override
    default void close(){
        dispose();
    }

}
