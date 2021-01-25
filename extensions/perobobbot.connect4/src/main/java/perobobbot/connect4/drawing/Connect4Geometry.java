package perobobbot.connect4.drawing;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.physics.Vector2D;

import java.awt.geom.Point2D;
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

    public static final int NUMBER_OF_COLUMNS = 7;
    public static final int NUMBER_OF_ROWS = 6;

    @Getter
    private final int numberOfColumns = NUMBER_OF_COLUMNS;
    @Getter
    private final int numberOfRows = NUMBER_OF_ROWS;

    @Getter
    private final int positionRadius;

    private final int spacing;

    private final int margin;

    public @NonNull Stream<Position> streamPositions() {
        return IntStream.range(0, NUMBER_OF_COLUMNS)
                        .boxed()
                        .flatMap(c -> IntStream.range(0, NUMBER_OF_ROWS).mapToObj(r ->  new Position(c, r)));
    }

    public int computeImageWidth() {
        return 2 * margin + NUMBER_OF_COLUMNS * (positionRadius * 2 + spacing) - spacing;
    }

    public int computeImageHeight() {
        return 2 * margin + NUMBER_OF_ROWS * (positionRadius * 2 + spacing) - spacing;
    }

    public @NonNull Vector2D computePositionAboveColumn(int columnIndex) {
        return computePositionOnImage(new Position(columnIndex,numberOfRows));
    }

    public @NonNull Vector2D computePositionOnImage(@NonNull Position position) {
        final var x = margin + positionRadius + position.getColumnIdx() * (positionRadius * 2 + spacing);
        final var y = margin + positionRadius + (NUMBER_OF_ROWS - position.getRowIdx()-1) * (positionRadius * 2 + spacing);
        return Vector2D.create(x, y);
    }
}
