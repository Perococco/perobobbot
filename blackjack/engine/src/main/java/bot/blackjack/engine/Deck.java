package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class Deck {

    public static final int MAXIMUM_NUMBER_OF_PACKET = 8;
    public static final int MINIMUM_NUMBER_OF_PACKET = 8;

    @NonNull
    public static Deck create(int numberOf52CardPackets) {
        final int effectiveNumberOfPackets = Math.min(MAXIMUM_NUMBER_OF_PACKET, Math.max(MINIMUM_NUMBER_OF_PACKET, numberOf52CardPackets));
        final ImmutableList.Builder<Card> builder = ImmutableList.builder();
        for (int i = 0; i < effectiveNumberOfPackets; i++) {
            builder.addAll(Arrays.asList(Card.values()));
        }
        return new Deck(builder.build());
    }

    @NonNull
    private final ImmutableList<Card> cards;

    public int size() {
        return cards.size();
    }

    @NonNull
    public Deck shuffled() {
        final List<Card> newCards = new ArrayList<>(cards);
        Collections.shuffle(newCards);
        return new Deck(ImmutableList.copyOf(newCards));
    }

    /**
     * Pick the first card of the deck
     * @return a pick result containing the picked card and the new deck
     */
    @NonNull
    public PickResult pickFirstCards(int numberOfCardsToPick) {
        if (cards.size() < numberOfCardsToPick) {
            throw new NotCardEnoughCardInDeck(numberOfCardsToPick, cards.size());
        }
        final ImmutableList<Card> firstCards = cards.subList(0,numberOfCardsToPick);
        final Deck newDeck = new Deck(cards.subList(numberOfCardsToPick+1,cards.size()));
        return new PickResult(newDeck,firstCards);
    }

}
