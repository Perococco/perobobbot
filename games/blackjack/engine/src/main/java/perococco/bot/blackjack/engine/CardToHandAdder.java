package perococco.bot.blackjack.engine;

import bot.blackjack.engine.Card;
import bot.blackjack.engine.Hand;
import bot.common.lang.ListTool;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CardToHandAdder {

    @NonNull
    public static CardAdder add(@NonNull Card card) {
        return new CardAdder(card);
    }

    @RequiredArgsConstructor
    public static class CardAdder {

        @NonNull
        private final Card card;

        @NonNull
        public Hand to(@NonNull Hand hand) {
            return hand.toBuilder().cards(ListTool.addLast(hand.cards(),card)).build();
        }
    }
}
