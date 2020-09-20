package perbobbot.blackjack.engine.action;

import lombok.NonNull;
import perbobbot.blackjack.engine.*;
import perbobbot.blackjack.engine.exception.BlackjackException;
import perbobbot.blackjack.engine.exception.InvalidHandForADouble;

public class Double extends DoOnPlayer {


    @NonNull
    public static Double with(String playerName) {
        return new Double(playerName);
    }


    private Double(@NonNull String playerName) {
        super(playerName);
    }


    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final Player player = handInfo.player();
        final OnePickResult pickResult = table.pickOneCard();
        final HandInfo newHand = handInfo.changeHand(h -> doubleHand(player,h,pickResult));
        return table.withReplacedHand(pickResult.getDeck(), newHand);
    }

    @NonNull
    private Hand doubleHand(@NonNull Player player, @NonNull Hand hand, @NonNull OnePickResult pickResult) {
        if (hand.isDone() || hand.isDealerHand() || hand.numberOfCards() != 2) {
            throw createException(player);
        }

        return hand.toBuilder()
                   .card(pickResult.getPickedCard())
                   .done(true)
                   .betAmount(hand.getBetAmount() * 2)
                   .build();
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForADouble(player);
    }
}
