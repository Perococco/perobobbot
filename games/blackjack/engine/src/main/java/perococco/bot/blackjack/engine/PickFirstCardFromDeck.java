package perococco.bot.blackjack.engine;

import bot.blackjack.engine.Card;
import bot.blackjack.engine.Deck;
import bot.blackjack.engine.EmptyDeck;
import lombok.NonNull;

public class PickFirstCardFromDeck {

    @NonNull
    public static PickResult pickCards(@NonNull Deck deck) {
        return new PickFirstCardFromDeck(deck).pick();
    }

    private final Deck currentDeck;

    private Card firstCard;

    private Deck newDeck;

    public PickFirstCardFromDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    /**
     * Pick the first card of the deck
     * @return a pick result containing the picked card and the new deck
     */
    @NonNull
    public PickResult pick() {
        this.checkTheDeckIsNotEmpty();
        this.getFirstCards();
        this.createNewDeck();
        return new PickResult(newDeck,firstCard);
    }

    private void getFirstCards() {
        firstCard = currentDeck.cards().get(0);
    }

    private void createNewDeck() {
        newDeck = new Deck(currentDeck.cards().subList(1,currentDeck.size()));
    }

    private void checkTheDeckIsNotEmpty() {
        if (currentDeck.isEmpty()) {
            throw new EmptyDeck();
        }
    }

}
