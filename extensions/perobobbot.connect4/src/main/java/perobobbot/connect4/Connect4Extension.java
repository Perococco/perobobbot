package perobobbot.connect4;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.connect4.game.*;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Looper;
import perobobbot.lang.MathTool;
import perobobbot.overlay.api.Overlay;
import perobobbot.rendering.Region;

import java.util.Optional;

public class Connect4Extension extends OverlayExtension {

    public static final String NAME = "Connect 4";

    private Connect4Game game;

    public Connect4Extension(@NonNull Overlay overlay) {
        super(NAME, overlay);
    }

    @Synchronized
    public void start(@NonNull Player.Factory player1Factory, @NonNull Player.Factory player2Factory, boolean bigMode) {
        if (game != null) {
            return;
        }

        final Connect4Grid grid = new Connect4Grid(40);
        final Connect4Overlay overlay = new Connect4Overlay(grid,bigMode?computeBigRegion():computeSmallRegion());
        attachClient(overlay);

        final var teamPlayer1 = (player1Factory.isAI()?Team.RED:Team.YELLOW);
        final var teamPlayer2 = teamPlayer1.getOtherType();

        final var players = MathTool.shuffle(
                player1Factory.create(teamPlayer1, overlay),
                player2Factory.create(teamPlayer2, overlay)
        );

        this.game = new Connect4Game(overlay, players.getFirst(), players.getSecond(), grid);
        this.game.start();
    }

    @Synchronized
    public void stop() {
        Optional.ofNullable(this.game)
                .ifPresent(Looper::requestStop);
        this.game = null;
        detachClient();
    }

    public Region computeSmallRegion() {
        var size = getOverlaySize();
        int w = 318*size.getWidth()/1600;
        int h = 660*size.getHeight()/900;

        return new Region(size.getWidth()-w, size.getHeight()-h, w, h);
    }

    public Region computeBigRegion() {
        var size = getOverlaySize();
        int w = size.getWidth()/3;
        int h = size.getHeight();

        return new Region((size.getWidth()-w)*0.5, 0, w, h);
    }
}
