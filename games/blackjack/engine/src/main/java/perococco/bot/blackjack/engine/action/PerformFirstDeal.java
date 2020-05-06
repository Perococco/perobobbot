package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.BlackjackTable;
import bot.blackjack.engine.Card;
import bot.blackjack.engine.Deck;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class PerformFirstDeal extends BlackjackAction {

    @NonNull
    public static BlackjackTable performFirstDeal(@NonNull BlackjackTable blackjackTable) {
        return new PerformFirstDeal(blackjackTable).mutate();
    }

    private Deck newDeck;

    private ImmutableList<Card> pickedCards;

    private BlackjackTable newTable;

    private PerformFirstDeal(@NonNull BlackjackTable currentTable) {
        super(currentTable);
    }

    @NonNull
    private BlackjackTable mutate() {
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
