package bot.blackjack.engine.exception;

import lombok.NonNull;

public class UnknownPlayer extends BlackjackException {

    @NonNull
    private final String playerName;

    public UnknownPlayer(@NonNull String playerName) {
        super("No player with name '"+playerName+"' is a the table");
        this.playerName = playerName;
    }
}
