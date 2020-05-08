package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.*;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForASplit;
import bot.common.lang.fp.Couple;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class Split extends DoOnPlayer {

    public Split(@NonNull String playerName) {
        super(playerName);
    }

    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final Player player = handInfo.player();
        final TwoPicksResult picksResult = table.pickTwoCards();
        return table.withReplacedHand(
                picksResult.deck(), handInfo.changeHands(hand -> this.splitHand(player, hand, picksResult))
        );
    }

    @NonNull
    private Couple<Hand> splitHand(@NonNull Player player, @NonNull Hand hand, @NonNull TwoPicksResult picksResult) {
        if (hand.done() || hand.dealer() || hand.numberOfCards() != 2) {
            throw createException(player);
        }
        final Card firstCard = hand.cardAt(0);
        final Card secondCard = hand.cardAt(1);
        if (firstCard.figure() != secondCard.figure()) {
            throw createException(player);
        }

        final Hand firstHand = buildSplitHand(hand, firstCard, picksResult.firstCard());
        final Hand secondHand = buildSplitHand(hand, secondCard, picksResult.secondCard());
        return Couple.of(firstHand, secondHand);
    }

    private Hand buildSplitHand(@NonNull Hand reference, @NonNull Card firstCard, @NonNull Card secondCard) {
        return reference.toBuilder().cards(ImmutableList.of(firstCard, secondCard)).fromASplit(true).build();
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForASplit(player);
    }
}
