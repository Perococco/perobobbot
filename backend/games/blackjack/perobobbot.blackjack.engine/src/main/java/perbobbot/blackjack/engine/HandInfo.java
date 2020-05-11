package perbobbot.blackjack.engine;

import perobobbot.common.lang.IndexedValue;
import lombok.NonNull;

public interface HandInfo {

    boolean playerHasName(@NonNull String playerName);

    @NonNull
    IndexedValue<Player> buildNewPlayer();

    @NonNull
    Player player();
}
