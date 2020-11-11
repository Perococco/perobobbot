package perobobbot.puckwar.physic;

import lombok.NonNull;
import perobobbot.overlay.api.OverlaySize;

import java.util.function.Predicate;

public class OutsiderPredicate implements Predicate<Puck> {

    private final int width;
    private final int height;

    public OutsiderPredicate(@NonNull OverlaySize overlaySize) {
        this.width = overlaySize.getWidth();
        this.height = overlaySize.getHeight();
    }

    @Override
    public boolean test(Puck puck) {
        final double x = puck.getPosition().x();
        final double y = puck.getPosition().y();
        final double radius = puck.getRadius();
        return x + radius < 0 || x - radius > width || y + radius < 0 || y - radius > height;
    }
}
