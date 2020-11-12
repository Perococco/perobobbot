package perobobbot.puckwar.game;

import lombok.NonNull;
import perobobbot.overlay.api.OverlaySize;

import java.awt.image.BufferedImage;

public class Target extends BufferedImage {

    public static @NonNull Target create(@NonNull OverlaySize overlaySize) {
        final int size = (int)Math.round(Math.min(overlaySize.getHeight()/10.,overlaySize.getWidth())/10.);
        return create(size);
    }

    public static @NonNull Target create(int size) {
        final var target = new Target(size);
        TargetDrawer.draw(target.createGraphics(),size);
        return target;
    }

    public Target(int size) {
        super(size, size, BufferedImage.TYPE_INT_ARGB);
    }
}
