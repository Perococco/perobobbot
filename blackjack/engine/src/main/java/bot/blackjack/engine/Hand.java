package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Hand {

    public static Hand create(@NonNull Card... cards) {
        return new Hand(ImmutableList.copyOf(cards));
    }

    public static final Hand EMPTY = new Hand(ImmutableList.of());

    @NonNull
    private final ImmutableList<Card> cards;

    public int value() {
        throw new RuntimeException("Not implemented yet");
    }


    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
