package perobobbot.puckwar.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.math.ImmutableVector2D;
import perobobbot.rendering.Size;

import java.util.Random;

@RequiredArgsConstructor
public class RandomPositionProvider {

    private final @NonNull Random random = new Random();
    private final @NonNull Size overlaySize;

    public @NonNull ImmutableVector2D compute(int margin, double xMinimalFraction, double xMaximalFraction) {
        final double x;
        final double y;

        {
            final double xmin = overlaySize.getWidth() * xMinimalFraction+margin;
            final double xmax = overlaySize.getWidth() * xMaximalFraction-margin;
            x = getRandomPosition(xmin, xmax);
        }
        {
            final double ymin = margin;
            final double ymax = overlaySize.getHeight()-margin;
            y = getRandomPosition(ymin,ymax);
        }

        return ImmutableVector2D.of(x,y);
    }

    private double getRandomPosition(double vmin, double vmax) {
        return random.nextDouble()*(vmax - vmin)+vmin;
    }

}
