package perobobbot.connect4;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.connect4.game.AI;
import perobobbot.connect4.game.Connect4Game;
import perobobbot.connect4.game.Connect4Grid;
import perobobbot.connect4.game.TwitchViewers;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.Looper;
import perobobbot.lang.MessageDispatcher;
import perobobbot.overlay.api.Overlay;
import perobobbot.rendering.Region;
import perobobbot.rendering.Size;

import java.time.Duration;
import java.util.Optional;

public class Connect4Extension extends OverlayExtension {

    public static final String NAME = "Connect 4";

    private Connect4Game game;

    private final MessageDispatcher messageDispatcher;

    public Connect4Extension(@NonNull Overlay overlay, @NonNull MessageDispatcher messageDispatcher) {
        super(NAME, overlay);
        this.messageDispatcher = messageDispatcher;
    }

    @Synchronized
    public void start() {
        if (game != null) {
            return;
        }

        final Connect4Grid grid = new Connect4Grid(40);
        final Connect4Overlay overlay = new Connect4Overlay(grid,computeSmallRegion());
        attachClient(overlay);

        final var player1 = new AI(TokenType.RED);
        final var player2 = new TwitchViewers(TokenType.YELLOW, overlay, messageDispatcher,Duration.ofSeconds(20));

        this.game = new Connect4Game(overlay, player1,player2, grid);
        this.game.start();
    }

    @Synchronized
    public void stop() {
        Optional.ofNullable(this.game).ifPresent(Looper::requestStop);
        this.game = null;
        detachClient();
    }

    public Region computeSmallRegion() {
        int w = 318;
        int h = 660;

        return new Region(1600-w, 900-h, w, h);
    }
}
