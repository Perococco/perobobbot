package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.*;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForADouble;
import bot.common.lang.ListTool;
import lombok.NonNull;

public class Double extends DoOnPlayer {

    public Double(@NonNull String playerName) {
        super(playerName);
    }

    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final Player player = handInfo.player();
        final OnePickResult pickResult = table.pickOneCard();
        return table.withReplacedHand(
                pickResult.deck(), handInfo.changeHand(hand -> doubleHand(player, hand, pickResult))
        );
    }

    @NonNull
    private Hand doubleHand(@NonNull Player player, @NonNull Hand hand, @NonNull OnePickResult pickResult) {
        if (hand.done() || hand.dealer() || hand.numberOfCards() != 2) {
            throw createException(player);
        }

        return hand.toBuilder()
                   .cards(ListTool.addLast(hand.cards(), pickResult.pickedCard()))
                   .done(true)
                   .betAmount(hand.betAmount() * 2)
                   .build();
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForADouble(player);
    }
}
