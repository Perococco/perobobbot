package perococco.perobobbot.blackjack.engine;

import perbobbot.blackjack.engine.Card;
import perbobbot.blackjack.engine.Deck;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeckFactory {

    @NonNull
    public static Deck custom(@NonNull Card...cards) {
        return new Deck(ImmutableList.copyOf(cards));
    }

    @NonNull
    public static Deck factoryOrder(int deckSize) {
        final ImmutableList<Card> cards = IntStream.range(0, deckSize)
                                                   .mapToObj(i -> Card.allCards())
                                                   .flatMap(Collection::stream)
                                                   .collect(ImmutableList.toImmutableList());
        return new Deck(cards);
    }

    @NonNull
    public static Deck shuffled(int deckSize) {
        final List<Card> cards = IntStream.range(0, deckSize)
                                          .mapToObj(i -> Card.allCards())
                                          .flatMap(Collection::stream)
                                          .collect(Collectors.toList());
        Collections.shuffle(cards);

        return new Deck(ImmutableList.copyOf(cards));
    }

}
