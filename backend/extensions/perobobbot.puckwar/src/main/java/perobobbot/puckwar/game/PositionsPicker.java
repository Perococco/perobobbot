package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.physics.ImmutableVector2D;
import perobobbot.rendering.Size;

import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionsPicker {

    public static Result pick(@NonNull Size overlaySize, @NonNull ImmutableVector2D initialPosition, int targetSize) {
        final var minimalDistance = Math.min(overlaySize.getHeight(), overlaySize.getWidth()) * 0.1;
        return new PositionsPicker(overlaySize, initialPosition, targetSize, minimalDistance).pick();
    }

    public static final int  MAX_NUMBER_OF_ITERATIONS = 100;

    @Value
    public static class Result {

        @NonNull ImmutableVector2D targetPosition;
        @NonNull ImmutableVector2D blackHolePosition;

        private Result swapPositions() {
            return new Result(blackHolePosition, targetPosition);
        }

    }

    private final Random random = new Random();

    private final Size overlaySize;
    private final @NonNull ImmutableVector2D initialPosition;
    private final int size;
    private final double minimalDistance;

    private @NonNull Result pick() {
        Result result = null;
        for (int i = 0; i < MAX_NUMBER_OF_ITERATIONS; i++) {
            result = new Result(pickOnePosition(),pickOnePosition());
            if (isTargetCloserToInitialPositionThanBlackHole(result)) {
                result = result.swapPositions();
            }
            if (areValidPositions(result)) {
                return result;
            }
        }
        return result;
    }

    private @NonNull ImmutableVector2D pickOnePosition() {
        return ImmutableVector2D.cartesian(pickWithMargin(overlaySize.getWidth()), pickWithMargin(overlaySize.getHeight()));
    }

    private int pickWithMargin(int size) {
        return this.size/2 +random.nextInt(size- this.size);
    }

    private boolean isTargetCloserToInitialPositionThanBlackHole(Result result) {
        return distanceToInitialPosition(result.blackHolePosition) > distanceToInitialPosition(result.targetPosition);
    }

    private double distanceToInitialPosition(@NonNull ImmutableVector2D vector) {
        return vector.normOfDifference(initialPosition);
    }

    private boolean areValidPositions(@NonNull Result result) {
        if (distanceToInitialPosition(result.blackHolePosition)<minimalDistance) {
            return false;
        }
        if (Math.abs(result.blackHolePosition.getX()-result.targetPosition.getX())< size) {
            return false;
        }
        return !(Math.abs(result.blackHolePosition.getY() - result.targetPosition.getY()) < size);
    }
}
