package bot.blackjack.engine;

import bot.common.lang.IndexedValue;
import bot.common.lang.fp.Couple;
import bot.common.lang.fp.Function1;
import bot.common.lang.fp.UnaryOperator1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingleHandInfo implements HandInfo {

    @NonNull
    private final IndexedValue<Player> player;

    @NonNull
    private final IndexedValue<Hand> hand;

    @Override
    public boolean playerHasName(@NonNull String playerName) {
        return player.getValue().hasName(playerName);
    }

    @NonNull
    @Override
    public IndexedValue<Player> buildNewPlayer() {
        return player.map(p -> p.withNewHand(hand));
    }


    @Override
    public @NonNull Player player() {
        return player.getValue();
    }

    public @NonNull Hand hand() {
        return hand.getValue();
    }

    @NonNull
    public SingleHandInfo changeHand(@NonNull UnaryOperator1<Hand> mutator) {
        return new SingleHandInfo(player, hand.map(mutator));
    }

    @NonNull
    public MultiHandInfo changeHands(@NonNull Function1<? super Hand, ? extends Couple<Hand>> mutator) {
        return new MultiHandInfo(player, hand.map(mutator));
    }
}
