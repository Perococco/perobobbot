package perococco.bot.blackjack.engine;

import bot.blackjack.engine.*;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformFirstDeal {

    @NonNull
    public static BlackjackTable performFirstDeal(@NonNull BlackjackTable blackjackTable) {
        return new PerformFirstDeal(blackjackTable).mutate();
    }

    @NonNull
    private final BlackjackTable currentTable;

    private Deck newDeck;

    private ImmutableList<Card> pickedCards;

    private BlackjackTable newTable;

    @NonNull
    private BlackjackTable mutate() {
        this.checkThatTheGameDidNotStart();

        this.pickAllCardsForFirstRound();
        assert newDeck != null && pickedCards != null;

        this.dealCardsToPlayersAndDealer();
        return newTable;
    }

    private void pickAllCardsForFirstRound() {
        final int numberOfCardsNeeded = 2*(currentTable.numberOfPlayers()+1);
        final PickResult pickResult = currentTable.deck().pickFirstCards(numberOfCardsNeeded);
        this.newDeck = pickResult.deck();
        this.pickedCards = pickResult.pickedCards();
    }

    private void dealCardsToPlayersAndDealer() {
        //TODO to do
    }

    private void checkThatTheGameDidNotStart() {
        //TODO avoid duplicate with {@link perococco.bot.blackjack.engine.AddNewPlayerToTable}
        if (currentTable.areAllHandsEmpty()) {
            return;
        }
        throw new GameAlreadyStarted("Perform first deal");
    }

}
