package perbobbot.blackjack.engine.exception;

import lombok.Getter;
import lombok.NonNull;
import perbobbot.blackjack.engine.Player;

public class InvalidHandForAStand extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForAStand(@NonNull Player player) {
        super("Cannot stand: "+player);
        this.player = player;
    }
}
