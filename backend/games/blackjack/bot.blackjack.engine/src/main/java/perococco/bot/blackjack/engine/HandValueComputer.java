package perococco.bot.blackjack.engine;

import bot.blackjack.engine.Card;
import bot.common.lang.MathTool;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HandValueComputer {

    public static int compute(@NonNull ImmutableList<Card> cards) {
        return new HandValueComputer(cards).computeValue();
    }

    @NonNull
    private final ImmutableList<Card> cards;

    public int computeValue() {
        int nbAces = 0;
        int baseValue = 0;
        for (Card card : cards) {
            if (card.isAnAce()) {
                nbAces++;
            }
            baseValue += Math.min(10,card.getFigure().getRank());
        }
        final int k = MathTool.clamp(Math.floorDiv(21 - baseValue, 10),0,nbAces);

        return baseValue+10*k;
    }
}
