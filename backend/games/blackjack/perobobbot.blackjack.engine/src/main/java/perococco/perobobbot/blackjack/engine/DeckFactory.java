package perococco.perobobbot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perbobbot.blackjack.engine.Card;
import perbobbot.blackjack.engine.Deck;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DeckFactory {

    @NonNull
    public static Deck custom(@NonNull Card... cards) {
        return new Deck(ImmutableList.copyOf(cards));
    }

    @NonNull
    public static Deck factoryOrder(int deckSize) {
        final ImmutableList<Card> cards = generateInFactoryOrder(deckSize)
                .collect(ImmutableList.toImmutableList());
        return new Deck(cards);
    }

    @NonNull
    public static Deck shuffled(int deckSize) {
        final List<Card> cards = generateInFactoryOrder(deckSize)
                .collect(Collectors.toList());
        Collections.shuffle(cards);

        return new Deck(ImmutableList.copyOf(cards));
    }

    private static @NonNull Stream<Card> generateInFactoryOrder(int deckSize) {
        return IntStream.range(0, deckSize)
                        .mapToObj(i -> Card.allCards())
                        .flatMap(Collection::stream);
    }
}
