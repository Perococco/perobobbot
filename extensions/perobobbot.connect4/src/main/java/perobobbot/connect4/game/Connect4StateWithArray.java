package perobobbot.connect4.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.ColumnFull;
import perobobbot.connect4.Connect4Exception;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.Team;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4StateWithArray implements Connect4State {

    public static @NonNull Connect4StateWithArray empty(int nbRows, int nbColumns) {
        final var freePositions = new int[nbColumns];
        Arrays.fill(freePositions, 0);
        return new Connect4StateWithArray(nbRows, nbColumns, null, new Team[nbRows * nbColumns], freePositions, null);
    }

    private final int nbRows;
    private final int nbColumns;
    private final Move lastMove;
    private final @NonNull Team[] data;
    private final int[] freePositions;
    private final WinningPosition winningPosition;
    private final int[] freeColumns;

    public boolean hasWinner() {
        return winningPosition != null;
    }

    public Connect4StateWithArray(int nbRows, int nbColumns, Move lastMove, @NonNull Team[] data, int[] freePositions, WinningPosition winningPosition) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.lastMove = lastMove;
        this.data = data;
        this.freePositions = freePositions;
        this.winningPosition = winningPosition;
        this.freeColumns = IntStream.range(0,nbColumns).filter(c -> freePositions[c]<nbRows).toArray();
    }

    public boolean isEmpty() {
        return lastMove == null;
    }

    public @NonNull Optional<Move> findLastMove() {
        return Optional.ofNullable(lastMove);
    }

    public @NonNull Optional<Integer> findColumnIndexOfLastMove() {
        return Optional.ofNullable(lastMove).map(Move::getColumnIndex);
    }

    public @NonNull Optional<Team> findTeamOfLastMove() {
        return Optional.ofNullable(lastMove).map(Move::getTeam);
    }

    public @NonNull Move getLastMove() {
        return findLastMove().orElseThrow(()->new Connect4Exception("No move done yet"));
    }

    public @NonNull int getColumnIndexOfLastMove() {
        return getLastMove().getColumnIndex();
    }

    public @NonNull Team getTeamOfLastMove() {
        return getLastMove().getTeam();
    }

    public boolean isFull() {
        return freeColumns.length == 0;
    }

    public boolean onlyOneFreeColumnLeft() {
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

    public @NonNull Optional<WinningPosition> getWinningPosition() {
        return Optional.ofNullable(winningPosition);
    }
    
    public @NonNull Optional<Team> getWinningTeam() {
        return Optional.ofNullable(winningPosition).map(WinningPosition::getWinningTeam);
    }

    public @NonNull GridPosition getFinalPosition(int columnIndex) {
        if (cannotPlayAt(columnIndex)) {
            throw new ColumnFull(columnIndex);
        }
        return new GridPosition(freePositions[columnIndex],columnIndex);
    }
    
    public Connect4StateWithArray withPlayAt(@NonNull Team type, int columnIndex) {
        if (cannotPlayAt(columnIndex)) {
            throw new ColumnFull(columnIndex);
        }
        final var newData = data.clone();
        final var newFreePositions = freePositions.clone();

        final var rowIndex = newFreePositions[columnIndex];

        newData[rowIndex + nbRows * columnIndex] = type;
        newFreePositions[columnIndex]++;

        final WinningPosition newWinningPosition;

        if (this.winningPosition != null) {
            newWinningPosition = winningPosition;
        } else {
            newWinningPosition = Connect4Evaluator.evaluate(newData, nbRows, nbColumns, rowIndex, columnIndex).orElse(null);
        }

        return new Connect4StateWithArray(nbRows, nbColumns, new Move(type, columnIndex), newData, newFreePositions, newWinningPosition);
    }

    public boolean cannotPlayAt(int columnIndex) {
        return freePositions[columnIndex] >= nbRows;
    }


}
