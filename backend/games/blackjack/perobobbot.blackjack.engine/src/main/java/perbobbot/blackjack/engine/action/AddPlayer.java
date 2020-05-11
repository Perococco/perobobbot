package perbobbot.blackjack.engine.action;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perbobbot.blackjack.engine.Player;
import perbobbot.blackjack.engine.Table;
import perbobbot.blackjack.engine.TableState;
import perbobbot.blackjack.engine.exception.InvalidBetAmount;
import perbobbot.blackjack.engine.exception.PlayerAlreadyAtTable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPlayer extends BlackjackAction {

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
