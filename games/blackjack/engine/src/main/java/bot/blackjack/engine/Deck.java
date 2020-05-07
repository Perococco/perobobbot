package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.DeckFactory;

@RequiredArgsConstructor
public class Deck {

    public static final int MAXIMUM_NUMBER_OF_PACKETS = 8;
    public static final int MINIMUM_NUMBER_OF_PACKETS = 1;

    public static Deck with(@NonNull Card... cards) {
        return new Deck(ImmutableList.copyOf(cards));
    }

    @NonNull
    public static Deck factoryOrder(int numberOf52CardPackets) {
        return DeckFactory.factoryOrder(numberOf52CardPackets);
    }

    @NonNull
    public static Deck shuffled(int numberOf52CardPackets) {
        return DeckFactory.shuffled(numberOf52CardPackets);
    }

    @NonNull
    @Getter
    private final ImmutableList<Card> cards;

    public int size() {
        return cards.size();
    }


    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
