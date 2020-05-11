package perbobbot.blackjack.engine;

import perobobbot.common.lang.IndexedValue;
import perobobbot.common.lang.ListTool;
import perobobbot.common.lang.fp.Couple;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

public class Player {

    @NonNull
    public static Player create(@NonNull String name, int initialBetAmount) {
        return new Player(name, initialBetAmount, ImmutableList.of(Hand.forPlayer(initialBetAmount)));
    }

    @NonNull
    @Getter
    private final String name;

    @Getter
    private final int initialBetAmount;

    @NonNull
    @Getter
    private final ImmutableList<Hand> hands;

    @Getter
    private final boolean done;

    public Player(@NonNull String name, int initialBetAmount, @NonNull ImmutableList<Hand> hands) {
        this.name = name;
        this.hands = hands;
        this.initialBetAmount = initialBetAmount;
        this.done = hands.stream().allMatch(Hand::isDone);
    }

    public boolean notDone() {
        return !done;
    }

    @NonNull
    public Optional<IndexedValue<Hand>> findFirstHandNotDone() {
        return ListTool.findFirst(hands, h -> !h.isDone());
    }

    @NonNull
    public Player withNewHand(@NonNull IndexedValue<Hand> newHand) {
        return withNewHands(newHand.insertInto(hands));
    }

    @NonNull
    public Player withNewHands(@NonNull IndexedValue<Couple<Hand>> newHands) {
        return withNewHands(ListTool.multiReplace(hands,newHands));
    }

    @NonNull
    private Player withNewHands(@NonNull ImmutableList<Hand> newHands) {
        return new Player(name,initialBetAmount,newHands);
    }

    public boolean hasName(String name) {
        return name.equals(this.name);
    }

    @NonNull
    public IndexedValue<Hand> handToDeal() {
        if (waitingForDeal()) {
            return IndexedValue.create(0,hands.get(0));
        }
        throw new IllegalStateException("Not hand waiting for a deal");
    }

    public boolean waitingForDeal() {
        return hands.size() == 1 && hands.get(0).hasLessThanTwoCards();
    }

    @Override
    public String toString() {
        return "Player{" +
               "name='" + name + '\'' +
               ", hands=" + hands +
               ", done=" + done +
               '}';
    }
}
