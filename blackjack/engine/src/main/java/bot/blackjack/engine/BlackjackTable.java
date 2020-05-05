package bot.blackjack.engine;

import bot.common.lang.MapTool;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perococco.bot.blackjack.engine.AddNewPlayerToTable;

@Builder(toBuilder = true)
public class BlackjackTable {

    @NonNull
    public static BlackjackTable create(int numberOf52CardPackets) {
        return new BlackjackTable(Deck.create(numberOf52CardPackets), Hand.EMPTY, ImmutableMap.of());
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
