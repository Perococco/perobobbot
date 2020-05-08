package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Player;
import bot.blackjack.engine.SingleHandInfo;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import bot.blackjack.engine.exception.BlackjackException;
import bot.common.lang.Mutation;
import lombok.NonNull;

public abstract class DoOnPlayer implements Mutation<Table> {

    @NonNull
    private final String playerName;

    protected DoOnPlayer(@NonNull String playerName) {
        this.playerName = playerName;
    }

    @Override
    public @NonNull Table mutate(@NonNull Table table) {
        final Player player = table.getPlayer(playerName);

        table.validateTableInState(TableState.PLAYER_PHASE);

        final SingleHandInfo handInfo = table.findFirstHandNotDoneOnTable()
                                              .filter(i -> i.playerHasName(playerName))
                                              .orElseThrow(() -> this.createException(player));

        return performAction(table, handInfo);
    }

    @NonNull
    protected abstract Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo);

    protected abstract BlackjackException createException(@NonNull Player player);


}
