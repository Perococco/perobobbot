package perbobbot.blackjack.engine.action;

import lombok.NonNull;
import perbobbot.blackjack.engine.Table;

public class StopGame extends BlackjackAction  {

    @NonNull
    public Table mutate(@NonNull Table table) {
        return table.withStoppedGame();
    }

}
