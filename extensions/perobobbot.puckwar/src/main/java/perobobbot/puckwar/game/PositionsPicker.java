package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.physics.ImmutableVector2D;
import perobobbot.rendering.ScreenSize;

import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionsPicker {

    public static Result pick(@NonNull ScreenSize overlaySize, int targetSize) {
        return new PositionsPicker(overlaySize, targetSize).pick();
    }

    public static final int MAX_NUMBER_OF_ITERATIONS = 100;

    @Value
    public static class Result {
        @NonNull ImmutableVector2D targetPosition;
        @NonNull ImmutableVector2D blackHolePosition;
    }

    private final Random random = new Random();

    private final ScreenSize overlaySize;
    private final int size;

    private @NonNull Result pick() {
        final var blackHoleDirection = pickDirection();
        final var targetDirection = pickDirection();
        final var blackHoleDistance = pickBetween(overlaySize.getMinLength() * 0.5, blackHoleDirection.getLength() * 0.6);
        final var targetDistance = pickBetween(blackHoleDirection.getLength() * 0.6 + size, targetDirection.getLength() - size*0.5);


        return new Result(
                computePosition(targetDistance, targetDirection.getAngle()),
                computePosition(blackHoleDistance, blackHoleDirection.getAngle()));
    }

    private Direction pickDirection() {
        final double angle = Math.toRadians(pickBetween(25, 65));

        final double length;
        final double height = Math.atan(angle) * overlaySize.getWidth();
        if (height <= overlaySize.getHeight()) {
            length = overlaySize.getWidth() / Math.cos(angle);
        } else {
            length = overlaySize.getHeight() / Math.sin(angle);
        }
        return new Direction(length, angle);
    }

    @Value
    private static class Direction {
        double length;
        double angle;
    }

    private double pickBetween(double vinf, double vsup) {
        if (vsup <= vinf) {
            return vsup;
        }
        return random.nextDouble() * (vsup - vinf) + vinf;
    }

    private ImmutableVector2D computePosition(double distance, double angle) {
        return ImmutableVector2D.cartesian(distance * Math.cos(angle), overlaySize.getHeight() - distance * Math.sin(angle));
    }

}
