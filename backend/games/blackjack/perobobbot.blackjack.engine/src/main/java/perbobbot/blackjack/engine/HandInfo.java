package perbobbot.blackjack.engine;

import lombok.NonNull;
import perobobbot.common.lang.IndexedValue;

public interface HandInfo {

    boolean playerHasName(@NonNull String playerName);

    @NonNull
    IndexedValue<Player> buildNewPlayer();

    @NonNull
    Player player();
}
