package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Player;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import bot.blackjack.engine.exception.PlayerAlreadyAtTable;
import bot.common.lang.Mutation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPlayer implements Mutation<Table> {

    private final String playerName;

    private final int betAmount;

    @Override
    public @NonNull Table mutate(@NonNull Table table) {
        table.validateTableInState(TableState.OPEN_TO_NEW_PLAYER);

        if (table.hasPlayer(playerName)) {
            throw new PlayerAlreadyAtTable(playerName);
        }

        return table.withNewPlayer(Player.create(playerName,betAmount));
    }

}
