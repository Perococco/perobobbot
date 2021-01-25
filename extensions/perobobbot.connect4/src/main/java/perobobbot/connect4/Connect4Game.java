package perobobbot.connect4;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.drawing.*;
import perobobbot.physics.Vector2D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4Game {

    @Getter
    private final @NonNull BufferedImage gridImage;

    private final @NonNull Connect4Geometry geometry;

    private final @NonNull Connect4GridState state;

    private final List<Token> tokens = new ArrayList<>();

    public Connect4Game(int positionRadius) {
        this.geometry = new Connect4Geometry(positionRadius,positionRadius/4,positionRadius/2);
        this.gridImage = new Connect4GridDrawer(geometry).draw();
        this.state = new Connect4GridState(geometry.getNumberOfRows(),geometry.getNumberOfColumns());
    }

    public @NonNull Vector2D computePositionOnImage(@NonNull Position position) {
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
    public void addTokenToGame(@NonNull TokenType type, int columnIndex) {
        final var finalPosition = state.getFirstFreePosition(columnIndex);
        this.tokens.add(new Token(type,geometry.getPositionRadius(),geometry.computePositionAboveColumn(columnIndex),geometry.computePositionOnImage(finalPosition)));
        this.state.setTokenType(type,finalPosition);
    }

    public @NonNull Stream<Token> streamTokens() {
        return tokens.stream();
    }
}
