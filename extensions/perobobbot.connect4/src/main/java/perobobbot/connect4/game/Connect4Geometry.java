package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.GridPosition;
import perobobbot.physics.Vector2D;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 0   1   2   3   4   5   6
 * 5| x | x | x | x | x | x | x |
 * 4|
 * 3|
 * 2|
 * 1|
 * 0|
 */
@RequiredArgsConstructor
public class Connect4Geometry {

    private final int numberOfColumns ;
    private final int numberOfRows;

    @Getter
    private final int positionRadius;

    private final int spacing;

    private final int margin;

    public @NonNull Stream<GridPosition> streamPositions() {
        return IntStream.range(0, numberOfColumns)
                        .boxed()
                        .flatMap(c -> IntStream.range(0, numberOfRows).mapToObj(r ->  new GridPosition(r, c)));
    }

    public int computeImageWidth() {
        return 2 * margin + numberOfColumns * (positionRadius * 2 + spacing) - spacing;
    }

    public int computeImageHeight() {
        return 2 * margin + numberOfRows * (positionRadius * 2 + spacing) - spacing;
    }

    public @NonNull Vector2D computePositionAboveColumn(int columnIndex) {
        return computePositionOnImage(new GridPosition(numberOfRows, columnIndex));
    }

    public @NonNull Vector2D computePositionOnImage(@NonNull GridPosition position) {
        final var x = margin + positionRadius + position.getColumnIdx() * (positionRadius * 2 + spacing);
        final var y = margin + positionRadius + (numberOfRows - position.getRowIdx()-1) * (positionRadius * 2 + spacing);
        return Vector2D.create(x, y);
    }
}
