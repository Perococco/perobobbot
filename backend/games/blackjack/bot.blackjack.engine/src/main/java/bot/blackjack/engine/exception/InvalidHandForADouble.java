package bot.blackjack.engine.exception;

import bot.blackjack.engine.Player;
import lombok.Getter;
import lombok.NonNull;

public class InvalidHandForADouble extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForADouble(@NonNull Player player) {
        super("Cannot double player bet: "+player);
        this.player = player;
    }
}
