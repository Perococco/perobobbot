package perobobbot.benchmark;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.math.ImmutableVector2D;
import perobobbot.rendering.Size;

import java.awt.*;
import java.util.Random;

@RequiredArgsConstructor
public class PuckFactory {

    private final Random random = new Random();

    private final int radius;

    private final @NonNull Size overlaySize;

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
        final int w = overlaySize.getWidth();
        final int h = overlaySize.getHeight();
        final ImmutableVector2D position = createRandomVector2D(w,h);
        final ImmutableVector2D velocity = createRandomVector2D(w,h).subtract(w/2.,h/2.).scale(0.1);
        return new Puck(position,velocity,getRandomColor(),radius);
    }

    private @NonNull Color getRandomColor() {
        return COLORS[random.nextInt(COLORS.length)];
    }

    private @NonNull ImmutableVector2D createRandomVector2D(int w, int h) {
        return ImmutableVector2D.of(random.nextInt(w),random.nextInt(h));
    }
}
