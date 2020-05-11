package perbobbot.blackjack.engine.exception;

import perbobbot.blackjack.engine.Player;
import lombok.Getter;
import lombok.NonNull;

public class InvalidHandForAStand extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForAStand(@NonNull Player player) {
        super("Cannot stand: "+player);
        this.player = player;
    }
}
