package perobobbot.puckwar.game;

import lombok.NonNull;
import perobobbot.rendering.Size;

import java.util.function.Predicate;

public class OutsiderPredicate implements Predicate<Puck> {

    private final int width;
    private final int height;

    public OutsiderPredicate(@NonNull Size overlaySize) {
        this.width = overlaySize.getWidth();
        this.height = overlaySize.getHeight();
    }

    @Override
    public boolean test(Puck puck) {
        final double x = puck.getPosition().getX();
        final double y = puck.getPosition().getY();
        final double radius = puck.getRadius();
        return x + radius < 0 || x - radius > width || y + radius < 0 || y - radius > height;
    }
}
