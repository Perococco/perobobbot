package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.math.Vector2D;
import perobobbot.rendering.Size;

import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetPositionComputer {

    public static final int MAXIMAL_NUMBER_OF_ITERATIONS = 10;

    private final @NonNull Random random = new Random();
    private final @NonNull Size overlaySize;
    private final @NonNull Vector2D initialPosition;
    private final int targetSize;

    public static @NonNull Vector2D compute(@NonNull Size overlaySize, @NonNull Vector2D initialPosition, int targetSize) {
        return new TargetPositionComputer(overlaySize, initialPosition,targetSize).compute();
    }

    private @NonNull Vector2D compute() {
        for (int i = 0; i < MAXIMAL_NUMBER_OF_ITERATIONS - 1; i++) {
            final Vector2D position = generateTargetPosition();
            if (position.subtract(initialPosition).manhattanNorm()>targetSize*4) {
                return position;
            }
        }
        return generateTargetPosition();
    }

    private @NonNull Vector2D generateTargetPosition() {
        return Vector2D.of(random.nextInt(overlaySize.getWidth()-targetSize*2)+targetSize, random.nextInt(overlaySize.getHeight()-2*targetSize)+targetSize);
    }



}
