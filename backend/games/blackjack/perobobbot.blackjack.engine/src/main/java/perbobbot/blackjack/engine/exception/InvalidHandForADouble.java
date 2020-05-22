package perbobbot.blackjack.engine.exception;

import lombok.Getter;
import lombok.NonNull;
import perbobbot.blackjack.engine.Player;

public class InvalidHandForADouble extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForADouble(@NonNull Player player) {
        super("Cannot double player bet: "+player);
        this.player = player;
    }
}
