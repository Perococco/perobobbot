package perbobbot.blackjack.engine;

import perobobbot.common.lang.IndexedValue;
import perobobbot.common.lang.fp.Couple;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MultiHandInfo implements HandInfo {

    @NonNull
    private final IndexedValue<Player> player;

    @NonNull
    private final IndexedValue<Couple<Hand>> hands;

    @Override
    public boolean playerHasName(@NonNull String playerName) {
        return player.getValue().hasName(playerName);
    }

    @Override
    public @NonNull Player player() {
        return player.getValue();
    }

    @NonNull
    @Override
    public IndexedValue<Player> buildNewPlayer() {
        return player.map(p -> p.withNewHands(hands));
    }
}
