package perbobbot.blackjack.engine;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Suit {
    DIAMONDS("♦"),
    CLUBS("♣"),
    HEARTS("♥"),
    SPADES("♠"),
    ;

    @NonNull
    @Getter
    private final String symbol;
}
