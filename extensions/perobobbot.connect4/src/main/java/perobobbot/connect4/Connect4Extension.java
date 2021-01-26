package perobobbot.connect4;

import lombok.NonNull;
import perobobbot.connect4.game.Connect4Game;
import perobobbot.connect4.game.TokenType;
import perobobbot.extension.OverlayExtension;
import perobobbot.overlay.api.Overlay;

import java.util.Optional;

public class Connect4Extension extends OverlayExtension {

    public static final String NAME = "Connect 4";

    private Connect4Game connect4Game = null;

    public Connect4Extension(@NonNull Overlay overlay) {
        super(NAME, overlay);
    }

    public void start() {
        this.connect4Game = new Connect4Game(40);
        final Connect4Overlay overlay = new Connect4Overlay(connect4Game);
        attachClient(overlay);
    }

    public void stop() {
        detachClient();
    }


    public void playAt(int columnIndex) {
        final var type = TokenType.RED;
        Optional.ofNullable(connect4Game).ifPresent(g -> g.addTokenToGame(type,columnIndex));
    }
}
