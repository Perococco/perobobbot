package perbobbot.blackjack.engine;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * The result after picking one card from a deck
 */
@RequiredArgsConstructor
@Getter
public class OnePickResult {

    /**
     * The new deck after the pick (i.e. the previous deck without the picked card
     */
    @NonNull
    private final Deck deck;

    /**
     * The card picked from the previous deck
     */
    @NonNull
    private final Card pickedCard;

    @NonNull
    public TwoPicksResult thenPickAgain() {
        final OnePickResult other = deck.pickOneCard();
        return new TwoPicksResult(other.deck, pickedCard, other.pickedCard);
    }
}
