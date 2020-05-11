package perbobbot.blackjack.engine.action;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perbobbot.blackjack.engine.*;
import perbobbot.blackjack.engine.exception.BlackjackException;
import perbobbot.blackjack.engine.exception.InvalidHandForASplit;
import perobobbot.common.lang.fp.Couple;

public class Split extends DoOnPlayer {


    @NonNull
    public static Split with(@NonNull String playerName) {
        return new Split(playerName);
    }


    private Split(@NonNull String playerName) {
        super(playerName);
    }

    @Override
    protected @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final Player player = handInfo.player();
        final TwoPicksResult picksResult = table.pickTwoCards();
        return table.withReplacedHand(
                picksResult.getDeck(),
                handInfo.changeHands(hand -> this.splitHand(player, hand, picksResult))
        );
    }

    @NonNull
    private Couple<Hand> splitHand(@NonNull Player player, @NonNull Hand hand, @NonNull TwoPicksResult picksResult) {
        if (hand.isDone() || hand.isDealerHand() || hand.numberOfCards() != 2) {
            throw createException(player);
        }
        final Card firstCard = hand.cardAt(0);
        final Card secondCard = hand.cardAt(1);
        if (firstCard.getFigure() != secondCard.getFigure()) {
            throw createException(player);
        }

        final Hand firstHand = buildSplitHand(hand, firstCard, picksResult.getFirstCard());
        final Hand secondHand = buildSplitHand(hand, secondCard, picksResult.getSecondCard());
        return Couple.of(firstHand, secondHand);
    }

    private Hand buildSplitHand(@NonNull Hand reference, @NonNull Card firstCard, @NonNull Card secondCard) {
        return reference.toBuilder().clearCards().cards(ImmutableList.of(firstCard, secondCard)).fromASplit(true).build();
    }

    @Override
    protected BlackjackException createException(@NonNull Player player) {
        return new InvalidHandForASplit(player);
    }
}
