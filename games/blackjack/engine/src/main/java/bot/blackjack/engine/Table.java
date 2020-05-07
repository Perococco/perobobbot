package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

@Builder(toBuilder = true)
public class Table {

    @NonNull
    public static Table create(int numberOf52CardPackets) {
        return create(Deck.factoryOrder(numberOf52CardPackets));
    }

    @NonNull
    public static Table create(Deck deck) {
        return new Table(TableState.OPEN_TO_NEW_PLAYER, deck, Hand.EMPTY, ImmutableList.of());
    }

    @Getter
    @NonNull
    private final TableState state;

    @Getter
    @NonNull
    private final Deck deck;

    @Getter
    @NonNull
    private final Hand dealerHand;

    @Getter
    @NonNull
    private final ImmutableList<Player> players;

    public boolean areAllHandsEmpty() {
        return dealerHand.isEmpty() && players.stream().allMatch(Player::hasNoCard);
    }

    public boolean hasPlayer(String playerName) {
        return players.stream().anyMatch(p -> p.name().equals(playerName));
    }

    public int numberOfPlayers() {
        return players.size();
    }

    @NonNull
    public Optional<Player> findPlayer(@NonNull String playerName) {
        return players.stream().filter(p -> p.name().equals(playerName)).findAny();
    }

    @NonNull
    public Player getPlayer(@NonNull String playerName) {
        return findPlayer(playerName).orElseThrow(() -> new UnknownPlayer(playerName));
    }
}
