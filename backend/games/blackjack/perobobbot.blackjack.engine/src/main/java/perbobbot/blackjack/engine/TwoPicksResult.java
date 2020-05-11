package perbobbot.blackjack.engine;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TwoPicksResult {

    @NonNull
    private final Deck deck;

    @NonNull
    private final Card firstCard;

    @NonNull
    private final Card secondCard;

}
