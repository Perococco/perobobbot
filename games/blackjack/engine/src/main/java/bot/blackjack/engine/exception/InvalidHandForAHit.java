package bot.blackjack.engine.exception;

import bot.blackjack.engine.Player;
import lombok.Getter;
import lombok.NonNull;

public class InvalidHandForAHit extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForAHit(@NonNull Player player) {
        super("Cannot deal a card to player: "+player);
        this.player = player;
    }
}
