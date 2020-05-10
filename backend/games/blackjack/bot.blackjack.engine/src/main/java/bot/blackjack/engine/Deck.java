package bot.blackjack.engine;

import bot.blackjack.engine.exception.EmptyDeck;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.DeckFactory;

@RequiredArgsConstructor
public class Deck {

    public static Deck with(@NonNull Card... cards) {
        return new Deck(ImmutableList.copyOf(cards));
    }

    @NonNull
    public static Deck factoryOrder(int deckSize) {
        return DeckFactory.factoryOrder(deckSize);
    }

    @NonNull
    public static Deck shuffled(int deckSize) {
        return DeckFactory.shuffled(deckSize);
    }

    @NonNull
    @Getter
    private final ImmutableList<Card> cards;

    @NonNull
    public OnePickResult pickOneCard() {
        if (cards.isEmpty()) {
            throw new EmptyDeck();
        }
        return new OnePickResult(tail(), firstCard());
    }

    @NonNull
    public TwoPicksResult pickTwoCards() {
        return pickOneCard().thenPickAgain();
    }

    private Deck tail() {
        return new Deck(cards.subList(1, cards.size()));
    }

    private Card firstCard() {
        return cards.get(0);
    }
}
