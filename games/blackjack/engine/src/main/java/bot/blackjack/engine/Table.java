package bot.blackjack.engine;

import bot.blackjack.engine.exception.InvalidTableState;
import bot.blackjack.engine.exception.UnknownPlayer;
import bot.common.lang.IndexedValue;
import bot.common.lang.ListTool;
import bot.common.lang.Printer;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import perococco.bot.blackjack.engine.TablePrinter;

import java.util.Optional;

public class Table {

    @NonNull
    public static Table create(@NonNull Deck deck, int tableSize) {
        return new Table(1,TableState.OPEN_TO_NEW_PLAYER, deck, tableSize, Hand.forDealer(), ImmutableList.of());
    }

    private final long id;

    @Getter
    @NonNull
    private final TableState state;

    private final int tableSize;

    @Getter
    @NonNull
    private final Deck deck;

    @Getter
    @NonNull
    private final Hand dealerHand;

    @Getter
    @NonNull
    private final ImmutableList<Player> players;


    private Table(long id, @NonNull TableState state,  @NonNull Deck deck, int tableSize, @NonNull Hand dealerHand, @NonNull ImmutableList<Player> players) {
        this.id = id;
        this.state = state;
        this.deck = deck;
        this.tableSize = tableSize;
        this.dealerHand = dealerHand;
        this.players = players;
    }

    @NonNull
    public Player getPlayer(@NonNull String playerName) {
        return players.stream()
                      .filter(p -> p.hasName(playerName))
                      .findFirst()
                      .orElseThrow(() -> new UnknownPlayer(playerName));
    }

    public boolean hasPlayer(String playerName) {
        return players.stream().anyMatch(p -> p.name().equals(playerName));
    }

    public boolean isFull() {
        return players.size() >= tableSize;
    }

    @NonNull
    public Table with(@NonNull Deck deck, @NonNull ImmutableList<Player> players) {
        return with(deck, dealerHand, players);
    }

    @NonNull
    public Table with(@NonNull Deck deck, @NonNull Hand dealerHand) {
        return with(deck, dealerHand, players);
    }

    @NonNull
    public Table withNewPlayer(@NonNull Player newPlayer) {
        return with(deck, ListTool.addLast(players, newPlayer));
    }

    @NonNull
    public Table withReplacedHand(@NonNull Deck deck, @NonNull HandInfo handInfo) {
        return withReplacedPlayer(deck, handInfo.buildNewPlayer());
    }

    @NonNull
    public Table withReplacedPlayer(@NonNull Deck deck, @NonNull IndexedValue<Player> indexedPlayer) {
        return with(deck, indexedPlayer.insertInto(players));
    }

    @NonNull
    public Table with(@NonNull Deck deck, @NonNull Hand dealerHand, @NonNull ImmutableList<Player> players) {
        final boolean allPlayerDone = players.stream().allMatch(Player::done);
        final boolean dealerDone = dealerHand().done();

        final TableState state = switch (this.state) {
            case OPEN_TO_NEW_PLAYER -> players.size() >= tableSize ? TableState.DEALING : TableState.OPEN_TO_NEW_PLAYER;
            case DEALING -> dealerHand.hasLessThanTwoCards() ? TableState.DEALING : TableState.PLAYER_PHASE;
            case PLAYER_PHASE -> allPlayerDone ? (dealerDone ? TableState.GAME_OVER : TableState.DEALER_PHASE) : TableState.PLAYER_PHASE;
            case DEALER_PHASE -> dealerDone ? TableState.GAME_OVER : TableState.DEALER_PHASE;
            case GAME_OVER -> TableState.GAME_OVER;
        };

        return new Table(id+1,state, deck, tableSize, dealerHand, players);
    }

    @NonNull
    public Optional<SingleHandInfo> findFirstHandNotDoneOnTable() {
        return ListTool.findFirst(players, Player::notDone)
                       .flatMap(
                               ip -> ip.value()
                                       .findFirstHandNotDone()
                                       .map(h -> new SingleHandInfo(ip, h))
                       );
    }

    @NonNull
    public Optional<SingleHandInfo> findFirstPlayerHandToDealTo() {
        return ListTool.findFirst(players, Player::waitingForDeal)
                       .map(ip -> new SingleHandInfo(ip, ip.value().handToDeal()));

    }

    public void validateTableInState(@NonNull TableState expectedTableState) {
        if (state() == expectedTableState) {
            return;
        }
        throw new InvalidTableState(expectedTableState, state());
    }

    @NonNull
    public OnePickResult pickOneCard() {
        return deck.pickOneCard();
    }

    @NonNull
    public TwoPicksResult pickTwoCards() {
        return deck.pickTwoCards();
    }

    public Table withStoppedGame() {
        return new Table(id+1,TableState.GAME_OVER, deck, tableSize, dealerHand, players);
    }

    public void dump(@NonNull Printer ps) {
        TablePrinter.print(this,ps);
    }

    public int size() {
        return tableSize;
    }

}
