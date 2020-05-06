package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Table;
import bot.blackjack.engine.Card;
import bot.blackjack.engine.Deck;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class PerformFirstDeal extends BlackjackAction {

    @NonNull
    public static Table performFirstDeal(@NonNull Table table) {
        return new PerformFirstDeal(table).mutate();
    }

    private Deck newDeck;

    private ImmutableList<Card> pickedCards;

    private Table newTable;

    private PerformFirstDeal(@NonNull Table currentTable) {
        super(currentTable);
    }

    @NonNull
    private Table mutate() {
        this.checkThatTheGameHasNotStartedYet("Perform first deal");

        this.pickAllCardsForFirstRound();
        assert newDeck != null && pickedCards != null;

        this.dealCardsToPlayersAndDealer();
        return newTable;
    }

    private void pickAllCardsForFirstRound() {
        final int numberOfCardsNeeded = 2*(currentTable().numberOfPlayers()+1);
        final PickResult pickResult = PickCardsFromDeck.pickCards(currentDeck(),numberOfCardsNeeded);
        this.newDeck = pickResult.deck();
        this.pickedCards = pickResult.pickedCards();
    }

    private void dealCardsToPlayersAndDealer() {

    }

}
