package bot.blackjack.engine.action;

import bot.blackjack.engine.InvalidHandForADouble;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.Table;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.TableHelper;

/**
 * Action to double a player hand
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DoublePlayerBetAmount {

    @NonNull
    public static Table execute(@NonNull Table table, @NonNull String playerName) {
        return new DoublePlayerBetAmount(TableHelper.with(table), playerName).execute();
    }

    @NonNull
    private final TableHelper helper;

    @NonNull
    private final String playerName;

    private Player player;

    @NonNull
    private Table execute() {
        player = helper.getPlayerFromItsName(playerName);
        this.checkHandIsValidForADouble();

        final Player newPlayer = player.withDoubleBetAmount();
        final Table table = helper.replacePlayer(newPlayer);
        return DealOneCardToPlayer.execute(table,playerName,0);
    }

    private void checkHandIsValidForADouble() {
        if (player.hasTwoCardsInOneHand()) {
            return;
        }
        throw new InvalidHandForADouble(playerName,player.hands());
    }
}
