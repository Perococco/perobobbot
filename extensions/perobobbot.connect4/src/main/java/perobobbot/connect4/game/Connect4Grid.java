package perobobbot.connect4.game;

import com.google.common.collect.ImmutableList;
import lombok.*;
import perobobbot.connect4.Connect4Constants;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.TokenType;
import perobobbot.lang.ListTool;
import perobobbot.lang.fp.Consumer1;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4Grid {

    @Getter
    private final @NonNull BufferedImage gridImage;

    private final @NonNull Connect4Geometry geometry;

    private ImmutableList<Token> tokens = ImmutableList.of();

    @Getter
    private final int numberOfColumns = Connect4Constants.NB_COLUMNS;
    @Getter
    private final int numberOfRows = Connect4Constants.NB_ROWS;

    private final int margin;
    private final int halfSpacing;

    public Connect4Grid(int positionRadius) {
        this.halfSpacing = positionRadius / 8;
        this.margin = positionRadius / 2;
        this.geometry = new Connect4Geometry(numberOfColumns, numberOfRows, positionRadius, halfSpacing * 2, margin + halfSpacing);
        this.gridImage = new Connect4GridDrawer(geometry).draw();
    }

    public void reset() {
        this.tokens = ImmutableList.of();
    }

    public void update(double dt) {
        forEachTokens(t -> t.computeNewPosition(dt));
    }

    public int getMargin() {
        return margin;
    }

    /**
     * Add a token to the game
     *
     * @param type        the type of the token to add
     * @param columnIndex the index of the column to token should be played
     */
    public void addTokenToGrid(@NonNull TokenType type, int columnIndex, @NonNull GridPosition finalPosition) {
        final var token = new Token(type,
                                    geometry.getPositionRadius()+2,
                                    geometry.computePositionAboveColumn(columnIndex),
                                    geometry.computePositionOnImage(finalPosition));
        this.tokens = ListTool.addLast(this.tokens, token);
    }

    public void forEachTokens(@NonNull Consumer1<? super Token> action) {
        tokens.forEach(action);
    }
}
