package perobobbot.connect4.game;

import lombok.*;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.TokenType;
import perobobbot.lang.fp.Consumer1;
import perobobbot.physics.Vector2D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4Game {

    @Getter
    private final @NonNull BufferedImage gridImage;

    private final @NonNull Connect4Geometry geometry;

    private @NonNull Connect4State state;

    private final List<Token> tokens = new ArrayList<>();

    public Connect4Game(int positionRadius) {
        this.geometry = new Connect4Geometry(positionRadius,positionRadius/4,positionRadius/2);
        this.gridImage = new Connect4GridDrawer(geometry).draw();
        this.state = Connect4State.empty(geometry.getNumberOfRows(), geometry.getNumberOfColumns());
    }

    public @NonNull Vector2D computePositionOnImage(@NonNull GridPosition position) {
        return geometry.computePositionOnImage(position);
    }

    public void update(double dt) {
        tokens.forEach(t -> t.computeNewPosition(dt));
    }


    /**
     * Add a token to the game
     * @param type the type of the token to add
     * @param columnIndex the index of the column to token should be played
     */
    @Synchronized
    public Optional<Connected4> addTokenToGame(@NonNull TokenType type, int columnIndex) {
        final var finalPosition = state.getFinalPosition(columnIndex);
        this.tokens.add(new Token(type,geometry.getPositionRadius(),geometry.computePositionAboveColumn(columnIndex),geometry.computePositionOnImage(finalPosition)));
        this.state = state.withPlayAt(type,columnIndex);
        return this.state.getWinningPosition();
    }

    public @NonNull Stream<Token> streamTokens() {
        return tokens.stream();
    }

    public void forEachTokens(@NonNull Consumer1<? super Token> action) {
        tokens.forEach(action);
    }
}
