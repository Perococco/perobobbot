package bot.blackjack.engine;

import bot.common.lang.IndexedValue;
import lombok.NonNull;

public interface HandInfo {

    boolean playerHasName(@NonNull String playerName);

    @NonNull
    IndexedValue<Player> buildNewPlayer();

    @NonNull
    Player player();
}
