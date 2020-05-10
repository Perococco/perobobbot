package bot.blackjack.engine.exception;

import lombok.NonNull;

public class GameAlreadyStarted extends BlackjackException {

    public GameAlreadyStarted(@NonNull String action) {
        super("Action cannot be performed after the game has been started: "+action);
    }
}
