package perobobbot.connect4;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.drawing.Displacement;
import perobobbot.connect4.drawing.Position;
import perobobbot.connect4.drawing.TokenType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class Connect4GridState {

    private final Map<Position, TokenType> tokenTypes = new HashMap<>();

    public static final int NUMBER_OF_CONNECTED_TO_WIN = 4;

    private final int nbRows;
    private final int nbColumns;

    private TokenType winner = null;

    public @NonNull Optional<TokenType> getWinner() {
        return Optional.ofNullable(winner);
    }

    public @NonNull Position getFirstFreePosition(int columnIndex) {
        validateColumnIndex(columnIndex);
        return IntStream.range(0, nbRows)
                        .mapToObj(rowIdx -> new Position(columnIndex, rowIdx))
                        .filter(position -> tokenTypes.get(position) == null)
                        .findFirst()
                        .orElseThrow(() -> new ColumnFull(columnIndex));
    }

    private void validateColumnIndex(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= nbColumns) {
            throw new InvalidColumnIndex(columnIndex);
        }
    }

    public void setTokenType(@NonNull TokenType type, @NonNull Position finalPosition) {
        this.validateColumnIndex(finalPosition.getColumnIdx());
        //TODO validate properly the row index

        final var currentValue = tokenTypes.get(finalPosition);
        if (currentValue != null) {
            throw new RuntimeException("Bug : the final position should be null");
        }
        tokenTypes.put(finalPosition, type);
        evaluateGameState(type,finalPosition);

    }

    private void evaluateGameState(@NonNull TokenType type, @NonNull Position finalPosition) {
        final var winningPosition = (Displacement.halfCircleDisplacements()
                                                 .map(dis -> countSameColor(type, finalPosition, dis))
                                                 .anyMatch(count -> count >= NUMBER_OF_CONNECTED_TO_WIN));

        if (winningPosition && this.winner == null) {
            this.winner = type;
        }

    }

    private boolean tokenAtPositionIsOfType(@NonNull Position p, @NonNull TokenType type) {
        return getTokenTypeAt(p).orElse(null) == type;
    }

    private @NonNull int countSameColor(@NonNull TokenType type, @NonNull Position referencePosition, @NonNull Displacement displacement) {
        final var left = countSameColorInDirection(type, referencePosition, displacement);
        final var right = countSameColorInDirection(type, referencePosition, displacement.negate());
        return Math.toIntExact((left + right) - 1);
    }

    private @NonNull int countSameColorInDirection(@NonNull TokenType type, @NonNull Position referencePosition, @NonNull Displacement displacement) {
        return Math.toIntExact(referencePosition.getPositionAlong(displacement)
                                                .takeWhile(p -> tokenAtPositionIsOfType(p, type))
                                                .count());
    }

    private @NonNull Optional<TokenType> getTokenTypeAt(@NonNull Position position) {
        return Optional.ofNullable(tokenTypes.get(position));
    }


}
