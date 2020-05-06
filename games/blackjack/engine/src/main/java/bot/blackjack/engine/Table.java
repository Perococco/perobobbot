package bot.blackjack.engine;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder(toBuilder = true)
public class Table {

    @NonNull
    public static Table create(int numberOf52CardPackets) {
        return new Table(Deck.factoryOrder(numberOf52CardPackets), Hand.EMPTY, ImmutableMap.of());
    }


    @Getter
    @NonNull
    private final Deck deck;

    @Getter
    @NonNull
    private final Hand dealer;

    @Getter
    @NonNull
    private final ImmutableMap<String,Player> players;



    public boolean areAllHandsEmpty() {
        return dealer.isEmpty() && players.values().stream().allMatch(Player::areHandsEmpty);
    }

    public boolean hasPlayer(String playerName) {
        return players.containsKey(playerName);
    }

    public int numberOfPlayers() {
        return players.size();
    }
}
