package bot.blackjack.engine;

import lombok.Getter;
import lombok.NonNull;

public class PlayerAlreadyAtTable extends BlackjackException {

    @NonNull
    @Getter
    private final String playerName;

    public PlayerAlreadyAtTable(@NonNull String playerName) {
        super("A player with the name '"+playerName+"' is alread at the table");
        this.playerName = playerName;
    }
}
