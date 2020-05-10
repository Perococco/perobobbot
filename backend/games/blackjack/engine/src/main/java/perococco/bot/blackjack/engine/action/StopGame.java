package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Table;
import bot.common.lang.Mutation;
import lombok.NonNull;

public class StopGame implements Mutation<Table>  {

    @NonNull
    public Table mutate(@NonNull Table table) {
        return table.withStoppedGame();
    }

}
