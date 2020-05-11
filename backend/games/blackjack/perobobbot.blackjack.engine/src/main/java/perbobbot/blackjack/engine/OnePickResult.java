package perbobbot.blackjack.engine;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OnePickResult {

    @NonNull
    private final Deck deck;

    @NonNull
    private final Card pickedCard;

    @NonNull
    public TwoPicksResult thenPickAgain() {
        final OnePickResult other = deck.pickOneCard();
        return new TwoPicksResult(other.deck, pickedCard, other.pickedCard);
    }
}
