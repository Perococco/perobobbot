package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.OnePickResult;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.SingleHandInfo;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForAHit;
import bot.common.lang.Mutation;
import lombok.NonNull;

public class HitPlayer extends DoOnPlayer {

    @NonNull
    public static HitPlayer with(@NonNull String playerName) {
        return new HitPlayer(playerName);
    }


    private HitPlayer(@NonNull String playerName) {
        super(playerName);
    }


    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final OnePickResult pickResult = table.pickOneCard();

        return table.withReplacedHand(
                pickResult.getDeck(),
                handInfo.changeHand(h -> h.addCard(pickResult.getPickedCard()))
        );
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForAHit(player);
    }

}
