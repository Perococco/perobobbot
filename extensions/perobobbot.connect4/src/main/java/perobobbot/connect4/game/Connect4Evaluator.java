package perobobbot.connect4.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.TokenType;

import java.util.Objects;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4Evaluator {

    public static final int NB_IN_A_ROW_TO_WIN = 4;

    public static Optional<Connected4> evaluate(@NonNull TokenType[] data, int nbRows, int nbColumns, int referenceRowIdx, int referenceColumnIdx) {
        return new Connect4Evaluator(data, nbRows, nbColumns, referenceRowIdx, referenceColumnIdx).testDirection();
    }

    private final @NonNull TokenType[] data;
    private final int nbRows;
    private final int nbColumns;
    private final int rowIdx;
    private final int columnIdx;

    private TokenType reference;


    private @NonNull Optional<Connected4> testDirection() {
        this.reference = getTokenType(rowIdx, columnIdx);
        if (this.reference == null) {
            return Optional.empty();
        }

        return Stream.<Supplier<Connected4>>of(
                this::testVertical,
                this::testHorizontal,
                this::testDiagonal1,
                this::testDiagonal2)
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findAny();
    }

    private Connected4 testVertical() {
        return testDirection(0, 1);
    }

    private Connected4 testHorizontal() {
        return testDirection(1, 0);
    }

    private Connected4 testDiagonal1() {
        return testDirection(-1, 1);
    }

    private Connected4 testDiagonal2() {
        return testDirection(1, 1);
    }


    private Connected4 testDirection(int dx, int dy) {
        final var n1 = nbSameInDirection(dx, dy);
        final var n2 = nbSameInDirection(-dx, -dy);
        if (n1 + n2 + 1 >= NB_IN_A_ROW_TO_WIN) {
            return new Connected4(reference,
                                  new GridPosition(rowIdx + n1 * dx, columnIdx + n1 * dy),
                                  new GridPosition(rowIdx - n2 * dx, columnIdx - n2 * dy)
            );
        }
        return null;
    }


    private int nbSameInDirection(int dx, int dy) {
        final IntPredicate isSameAsReference = i -> getTokenType(rowIdx + i * dx, columnIdx + i * dy) == reference;
        return (int) (IntStream.iterate(1, isSameAsReference::test, i -> i + 1).count());
    }

    private TokenType getTokenType(int rowIdx, int columnIdx) {
        if (rowIdx < 0 || rowIdx >= nbRows) {
            return null;
        } else if (columnIdx < 0 || columnIdx >= nbColumns) {
            return null;
        }
        return data[columnIdx * nbRows + rowIdx];
    }

}
