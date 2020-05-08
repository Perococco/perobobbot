package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.SingleHandInfo;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForAStand;
import lombok.NonNull;

public class Stand extends DoOnPlayer {

    public Stand(@NonNull String playerName) {
        super(playerName);
    }

    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        if (handInfo.hand().done()) {
            return table;
        }
        return table.withReplacedHand(
                table.deck(),
                handInfo.changeHand(Hand::withSetDone)
        );
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForAStand(player);
    }
}
