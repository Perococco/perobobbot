package perococco.bot.blackjack.engine;

import bot.blackjack.engine.*;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ShuffleDeck {

    @NonNull
    public static Deck shuffle(@NonNull Deck deck) {
        return new ShuffleDeck(deck).shuffled();
    }


    @NonNull
    private final Deck currentDeck;

    @NonNull
    public Deck shuffled() {
        final List<Card> newCards = new ArrayList<>(currentDeck.cards());
        Collections.shuffle(newCards);
        return new Deck(ImmutableList.copyOf(newCards));
    }

}
