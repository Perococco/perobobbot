package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.ColumnFull;
import perobobbot.connect4.InvalidColumnIndex;
import perobobbot.lang.PerobobbotException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class Connect4GameState {

    public static final int NUMBER_TO_CONNECT_TO_WIN = 4;

    private final int nbRows;
    private final int nbColumns;
    private final @NonNull Map<GridPosition, TokenType> tokenTypes = new HashMap<>();

    private TokenType winner = null;

    public @NonNull Optional<TokenType> getWinner() {
        return Optional.ofNullable(winner);
    }

    public boolean hasAWinner() {
        return winner != null;
    }

    /**
     * @param columnIndex the index of the column to search
     * @return the first free position on that column. This is the position the token will have if played on that column
     * @throws ColumnFull is no more room is available in the column with the provided index
     */
    public @NonNull GridPosition getFirstFreePosition(int columnIndex) {
        validateColumnIndex(columnIndex);
        return IntStream.range(0, nbRows)
                        .mapToObj(GridPosition.factoryForColumn(columnIndex))
                        .filter(this::isFreePosition)
                        .findFirst()
                        .orElseThrow(() -> new ColumnFull(columnIndex));
    }

    /**
     * Set the token type at a given position. Calling this method will modify
     * the state of the game. It might also set the winner of the game if
     * a "connect 4" is made by playing at this position.
     *
     * @param type the type of the token to put
     * @param targetPosition the position of the token
     */
    public void putTokenType(@NonNull TokenType type, @NonNull GridPosition targetPosition) {
        this.validateColumnIndex(targetPosition.getColumnIdx());
        this.validateRowIndex(targetPosition.getRowIdx());

        final var currentValue = tokenTypes.get(targetPosition);
        if (currentValue != null) {
            throw new PerobobbotException("Bug : the final position should be free");
        }
        tokenTypes.put(targetPosition, type);
        evaluateWinner(type, targetPosition);
    }


    private void validateColumnIndex(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= nbColumns) {
            throw new InvalidColumnIndex(columnIndex);
        }
    }

    private void validateRowIndex(int rowIdx) {
        if (rowIdx < 0 || rowIdx >= nbRows) {
            throw new PerobobbotException("[Bug] invalid row index: "+rowIdx);
        }
    }

    private void evaluateWinner(@NonNull TokenType type, @NonNull GridPosition finalPosition) {
        if (this.hasAWinner()) {
            return;
        }
        final var winningPosition = Direction.halfCircleDisplacements()
                                             .map(dis -> countSameType(type, finalPosition, dis))
                                             .anyMatch(count -> count >= NUMBER_TO_CONNECT_TO_WIN);

        if (winningPosition) {
            this.winner = type;
        }

    }

    private boolean isFreePosition(@NonNull GridPosition position) {
        return tokenTypes.get(position) == null;
    }

    private boolean tokenAtPositionIsOfType(@NonNull GridPosition position, @NonNull TokenType type) {
        return tokenTypes.get(position) == type;
    }

    private @NonNull int countSameType(@NonNull TokenType type, @NonNull GridPosition referencePosition, @NonNull Direction direction) {
        final var left = countSameColorInDirection(type, referencePosition, direction);
        final var right = countSameColorInDirection(type, referencePosition, direction.reverse());
        return Math.toIntExact((left + right) - 1);
    }

    private @NonNull int countSameColorInDirection(@NonNull TokenType type, @NonNull GridPosition referencePosition, @NonNull Direction direction) {
        return Math.toIntExact(referencePosition.getGridPositionAlong(direction)
                                                .takeWhile(p -> tokenAtPositionIsOfType(p, type))
                                                .count());
    }

    private @NonNull Optional<TokenType> getTokenTypeAt(@NonNull GridPosition position) {
        return Optional.ofNullable(tokenTypes.get(position));
    }


}
