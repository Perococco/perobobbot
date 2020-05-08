package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.OnePickResult;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.SingleHandInfo;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForAHit;
import lombok.NonNull;

public class HitPlayer extends DoOnPlayer {

    public HitPlayer(@NonNull String playerName) {
        super(playerName);
    }


    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final OnePickResult pickResult = table.pickOneCard();

        return table.withReplacedHand(
                pickResult.deck(),
                handInfo.changeHand(h -> h.addCard(pickResult.pickedCard()))
        );
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForAHit(player);
    }

}
