package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Hand {

    public static final Hand EMPTY = new Hand(ImmutableList.of());

    @NonNull
    private final ImmutableList<Card> cards;


    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
