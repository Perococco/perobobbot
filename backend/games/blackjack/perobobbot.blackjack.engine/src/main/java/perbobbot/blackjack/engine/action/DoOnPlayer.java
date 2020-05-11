package perbobbot.blackjack.engine.action;

import lombok.NonNull;
import perbobbot.blackjack.engine.Player;
import perbobbot.blackjack.engine.SingleHandInfo;
import perbobbot.blackjack.engine.Table;
import perbobbot.blackjack.engine.TableState;
import perbobbot.blackjack.engine.exception.BlackjackException;

public abstract class DoOnPlayer extends BlackjackAction {

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

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
               "playerName='" + playerName + '\'' +
               '}';
    }
}
