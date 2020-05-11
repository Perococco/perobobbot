package perbobbot.blackjack.engine.action;

import lombok.NonNull;
import perbobbot.blackjack.engine.OnePickResult;
import perbobbot.blackjack.engine.Player;
import perbobbot.blackjack.engine.SingleHandInfo;
import perbobbot.blackjack.engine.Table;
import perbobbot.blackjack.engine.exception.BlackjackException;
import perbobbot.blackjack.engine.exception.InvalidHandForAHit;

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
