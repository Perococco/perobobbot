package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Player {

    @NonNull
    public static Player create(@NonNull String name, int betAmount) {
        return new Player(name,betAmount,ImmutableList.of());
    }

    @NonNull
    @Getter
    private final String name;

    @Getter
    private final int betAmount;

    @NonNull
    private final ImmutableList<Hand> hands;

    public boolean areHandsEmpty() {
        return hands.stream().allMatch(Hand::isEmpty);
    }
}
