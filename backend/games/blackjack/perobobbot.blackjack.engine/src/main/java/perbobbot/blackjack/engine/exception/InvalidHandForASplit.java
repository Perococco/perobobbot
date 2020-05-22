package perbobbot.blackjack.engine.exception;

import lombok.Getter;
import lombok.NonNull;
import perbobbot.blackjack.engine.Player;

public class InvalidHandForASplit extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForASplit(@NonNull Player player) {
        super("Cannot split: "+player);
        this.player = player;
    }
}
