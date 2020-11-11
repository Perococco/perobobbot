package perobobbot.benchmark;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.math.Vector2D;
import perobobbot.overlay.api.OverlaySize;

import java.awt.*;
import java.util.Random;

@RequiredArgsConstructor
public class PuckFactory {

    private final Random random = new Random();

    private final int radius;

    private final OverlaySize overlaySize;

    private static final Color[] COLORS = {
            Color.ORANGE,
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.PINK
    };

    public @NonNull Puck create(int puckIndex) {
        final Vector2D position =createRandomVector2D();
        final Vector2D velocity = createRandomVector2D().scale(0.1);
        return new Puck(position,velocity,getRandomColor(),radius);
    }

    private @NonNull Color getRandomColor() {
        return COLORS[random.nextInt(COLORS.length)];
    }

    private Vector2D createRandomVector2D() {
        return Vector2D.of(random.nextInt(overlaySize.getWidth()),random.nextInt(overlaySize.getHeight()));
    }
}
