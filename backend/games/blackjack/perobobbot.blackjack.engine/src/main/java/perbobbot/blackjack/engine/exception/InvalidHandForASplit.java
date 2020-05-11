package perbobbot.blackjack.engine.exception;

import perbobbot.blackjack.engine.Player;
import lombok.Getter;
import lombok.NonNull;

public class InvalidHandForASplit extends BlackjackException {

    @NonNull
    @Getter
    private final Player player;

    public InvalidHandForASplit(@NonNull Player player) {
        super("Cannot split: "+player);
        this.player = player;
    }
}
