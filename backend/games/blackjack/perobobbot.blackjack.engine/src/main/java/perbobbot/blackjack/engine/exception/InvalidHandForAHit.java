package perbobbot.blackjack.engine.exception;

import lombok.Getter;
import lombok.NonNull;
import perbobbot.blackjack.engine.Player;

public class InvalidHandForAHit extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForAHit(@NonNull Player player) {
        super("Cannot deal a card to player: "+player);
        this.player = player;
    }
}
