package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

@Builder(toBuilder = true)
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
    @Singular
    @Getter
    private final ImmutableList<Hand> hands;

    public boolean hasNoCard() {
        return hands.stream().allMatch(Hand::isEmpty);
    }

    @NonNull
    public Player withNewHands(@NonNull ImmutableList<Hand> hands) {
        return toBuilder().hands(hands).build();
    }

    @NonNull
    public Player withDoubleBetAmount() {
        return toBuilder().betAmount(betAmount*2).build();
    }

    @NonNull
    public Hand handAt(int index) {
        return hands.get(index);
    }

    public boolean hasTwoCardsInOneHand() {
        return hands.size() == 1 && hands.get(0).numberOfCards() == 2;
    }

    public int numberOfHands() {
        return hands.size();
    }

    public boolean hasName(String name) {
        return name.equals(this.name);
    }
}
