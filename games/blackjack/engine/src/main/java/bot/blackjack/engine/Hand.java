package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder(toBuilder = true)
public class Hand {

    public static Hand of(@NonNull Card... cards) {
        return new Hand(ImmutableList.copyOf(cards));
    }

    public static final Hand EMPTY = new Hand(ImmutableList.of());

    @NonNull
    @Getter
    private final ImmutableList<Card> cards;

    public int value() {
        throw new RuntimeException("Not implemented yet");
    }

    public int numberOfCards() {
        return cards.size();
    }

    @NonNull
    public Card cardAt(int index) {
        return cards.get(index);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean isBusted() {
        return value()>21;
    }

}
