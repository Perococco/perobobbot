package perobobbot.connect4.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.ColumnFull;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.TokenType;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4State {

    public static @NonNull Connect4State empty(int nbRows, int nbColumns) {
        final var freePositions = new int[nbColumns];
        Arrays.fill(freePositions, 0);
        return new Connect4State(nbRows, nbColumns, new TokenType[nbRows * nbColumns], freePositions, null);
    }

    private final int nbRows;
    private final int nbColumns;
    private final @NonNull TokenType[] data;
    private final int[] freePositions;
    private final Connected4 winningPosition;
    private final int[] freeColumns;


    public boolean hasWinner() {
        return winningPosition != null;
    }

    public Connect4State(int nbRows, int nbColumns, @NonNull TokenType[] data, int[] freePositions, Connected4 winningPosition) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.data = data;
        this.freePositions = freePositions;
        this.winningPosition = winningPosition;
        this.freeColumns = IntStream.range(0,nbColumns).filter(c -> freePositions[c]<nbRows).toArray();
    }

    public boolean isFull() {
        return freeColumns.length == 0;
    }

    public boolean onlyOneColumnLeft() {
        return freeColumns.length == 1;
    }

    public @NonNull IntStream streamIndicesOfFreeColumns() {
        return Arrays.stream(freeColumns);
    }

    public @NonNull int[] getIndicesOfFreeColumns() {
        return freeColumns.clone();
    }

    public int pickOneColumn(@NonNull Random random) {
        if (freeColumns.length == 0) {
            return -1;
        }
        return freeColumns[random.nextInt(freeColumns.length)];
    }

    public int pickOneColumn() {
        return pickOneColumn(new Random());
    }

    public @NonNull Optional<Connected4> getWinningPosition() {
        return Optional.ofNullable(winningPosition);
    }
    
    public @NonNull GridPosition getFinalPosition(int columnIndex) {
        if (!canPlayAt(columnIndex)) {
            throw new ColumnFull(columnIndex);
        }
        return new GridPosition(freePositions[columnIndex],columnIndex);
    }
    
    public Connect4State withPlayAt(@NonNull TokenType type, int columnIndex) {
        if (!canPlayAt(columnIndex)) {
            throw new ColumnFull(columnIndex);
        }
        final var newData = data.clone();
        final var newFreePositions = freePositions.clone();

        final var rowIndex = newFreePositions[columnIndex];

        newData[rowIndex + nbRows * columnIndex] = type;
        newFreePositions[columnIndex]++;

        final Connected4 newWinningPosition;

        if (this.winningPosition != null) {
            newWinningPosition = winningPosition;
        } else {
            newWinningPosition = Connect4Evaluator.evaluate(newData, nbRows, nbColumns, rowIndex, columnIndex).orElse(null);
        }

        return new Connect4State(nbRows, nbColumns, newData, newFreePositions, newWinningPosition);
    }

    public boolean canPlayAt(int columnIndex) {
        return freePositions[columnIndex] < nbRows;
    }


}
