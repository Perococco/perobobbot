package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Player;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import bot.blackjack.engine.exception.InvalidBetAmount;
import bot.blackjack.engine.exception.PlayerAlreadyAtTable;
import bot.common.lang.Mutation;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPlayer implements Mutation<Table> {

    @NonNull
    public static AddPlayer with(String playerName, int betAmount) {
        if (betAmount<=0) {
            throw new InvalidBetAmount(playerName, betAmount);
        }
        return new AddPlayer(playerName,Math.min(100,betAmount));
    }

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
