package perococco.perobobbot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perbobbot.blackjack.engine.Card;
import perobobbot.common.lang.MathTool;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HandValueComputer {

    public static int compute(@NonNull ImmutableList<Card> cards) {
        return new HandValueComputer(cards).computeValue();
    }

    @NonNull
    private final ImmutableList<Card> cards;

    public int computeValue() {
        int nbAcesInHand = 0;
        int baseValue = 0;

        //uses 1 for Ace.
        for (Card card : cards) {
            if (card.isAnAce()) {
                nbAcesInHand++;
            }
            baseValue += Math.min(10,card.getFigure().getRank());
        }

        final int leftToReach21 = 21-baseValue;
        if (leftToReach21<=0) {
            return baseValue;
        }
        final int nbAcesToFillTheMargin = Math.min(leftToReach21/10,nbAcesInHand);

        return baseValue+10*nbAcesToFillTheMargin;
    }
}
