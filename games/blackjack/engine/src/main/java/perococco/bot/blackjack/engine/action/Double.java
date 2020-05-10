package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.*;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForADouble;
import lombok.NonNull;

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
        return table.withReplacedHand(
                pickResult.getDeck(), handInfo.changeHand(hand -> doubleHand(player, hand, pickResult))
        );
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
