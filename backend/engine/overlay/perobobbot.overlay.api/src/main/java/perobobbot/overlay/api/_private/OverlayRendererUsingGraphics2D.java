package perobobbot.overlay.api._private;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.overlay.api.OverlayRenderer;
import perobobbot.overlay.api.OverlaySize;

import java.awt.*;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class OverlayRendererUsingGraphics2D implements OverlayRenderer {

    private final @NonNull Graphics2D graphics2D;

    @Getter
    private final @NonNull OverlaySize overlaySize;

    @Override
    public void dispose() {
        graphics2D.dispose();
    }

    @Override
    public @NonNull Color getBackground() {
        return graphics2D.getColor();
    }

    @Override
    public @NonNull OverlayRenderer setBackground(@NonNull Color color) {
        graphics2D.setBackground(color);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer clearOverlay() {
        final Color backup = graphics2D.getBackground();
        graphics2D.setBackground(TRANSPARENT);
        graphics2D.clearRect(0,0,overlaySize.getWidth(),overlaySize.getHeight());
        graphics2D.setBackground(backup);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer translate(double x, double y) {
        graphics2D.translate(x,y);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer setColor(@NonNull Color color) {
        graphics2D.setColor(color);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer fillCircle(int xc, int yc, int radius) {
        graphics2D.fillOval(xc-radius,yc-radius,radius*2,radius*2);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer withPrivateContext(Consumer1<? super OverlayRenderer> drawer) {
        try (OverlayRenderer r = new OverlayRendererUsingGraphics2D((Graphics2D)graphics2D.create(),overlaySize)) {
            drawer.f(r);
        }
        return this;
    }

    @Override
    public @NonNull OverlayRenderer drawImage(@NonNull BufferedImage image, int x, int y) {
        graphics2D.drawImage(image,x,y,null);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer drawImage(@NonNull BufferedImage image, int x, int y, @NonNull Color bkgColor) {
        graphics2D.drawImage(image,x,y,bkgColor,null);
        return this;
    }

    @Override
    public @NonNull OverlayRenderer fillRect(int x, int y, int width, int height) {
        graphics2D.fillRect(x,y,width,height);
        return this;
    }
}
