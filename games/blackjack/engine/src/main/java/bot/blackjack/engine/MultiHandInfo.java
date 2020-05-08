package bot.blackjack.engine;

import bot.common.lang.IndexedValue;
import bot.common.lang.fp.Couple;
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
        return player.value().hasName(playerName);
    }

    @Override
    public @NonNull Player player() {
        return player.value();
    }

    @NonNull
    @Override
    public IndexedValue<Player> buildNewPlayer() {
        return player.map(p -> p.withNewHands(hands));
    }
}
