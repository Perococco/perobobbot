package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Card;
import bot.blackjack.engine.Deck;
import bot.blackjack.engine.NotCardEnoughCardInDeck;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PickCardsFromDeck {

    @NonNull
    public static PickResult pickCards(@NonNull Deck deck, int numberOfCards) {
        return new PickCardsFromDeck(deck,numberOfCards).pick();
    }

    private final Deck currentDeck;

    private final int numberOfCardsToPick;

    private ImmutableList<Card> firstCards;

    private Deck newDeck;

    /**
     * Pick the first card of the deck
     * @return a pick result containing the picked card and the new deck
     */
    @NonNull
    public PickResult pick() {
        this.checkThereAreEnoughCards();
        this.getFirstCards();
        this.createNewDeck();
        return new PickResult(newDeck,firstCards);
    }

    private void getFirstCards() {
        firstCards = currentDeck.cards().subList(0,numberOfCardsToPick);
    }

    private void createNewDeck() {
        newDeck = new Deck(currentDeck.cards().subList(numberOfCardsToPick+1,currentDeck.size()));
    }

    private void checkThereAreEnoughCards() {
        if (currentDeck.size() < numberOfCardsToPick) {
            throw new NotCardEnoughCardInDeck(numberOfCardsToPick, currentDeck.size());
        }
    }

}
