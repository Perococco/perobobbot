package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.math.Vector2D;
import perobobbot.rendering.Size;

import java.util.Random;

@RequiredArgsConstructor
public class RandomPositionProvider {

    private final @NonNull Random random = new Random();
    private final @NonNull Size overlaySize;

    public @NonNull Vector2D compute(int objectSize, double xMinimalFraction, double xMaximalFraction) {
        final double x;
        final double y;

        {
            final double xmin = overlaySize.getWidth() * xMinimalFraction;
            final double xmax = overlaySize.getWidth() * xMaximalFraction;
            x = getRandomPosition(xmin, xmax);
        }
        {
            final double ymin = objectSize;
            final double ymax = overlaySize.getHeight()-objectSize*2;
            y = getRandomPosition(ymin,ymax);
        }

        return Vector2D.of(x,y);
    }

    private double getRandomPosition(double vmin, double vmax) {
        return random.nextDouble()*(vmax - vmin)+vmin;
    }

}
