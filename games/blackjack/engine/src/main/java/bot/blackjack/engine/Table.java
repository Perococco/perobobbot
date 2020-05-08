package bot.blackjack.engine;

import bot.blackjack.engine.exception.InvalidTableState;
import bot.blackjack.engine.exception.UnknownPlayer;
import bot.common.lang.IndexedValue;
import bot.common.lang.ListTool;
import bot.common.lang.Printer;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class Table {

    @NonNull
    public static Table create(@NonNull Deck deck, int tableSize) {
        return new Table(TableState.OPEN_TO_NEW_PLAYER, deck, tableSize, Hand.forDealer(), ImmutableList.of());
    }

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


    private Table(@NonNull TableState state, @NonNull Deck deck, int tableSize, @NonNull Hand dealerHand, @NonNull ImmutableList<Player> players) {
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

        return new Table(state, deck, tableSize, dealerHand, players);
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
        return new Table(TableState.GAME_OVER, deck, tableSize, dealerHand, players);
    }

    public static final String EMPTY_PLAYER = "Empty";

    public void dump(@NonNull Printer ps) {
        ps.println(state)
          .println();

        final int maxPlayerNameLength = players.stream().mapToInt(p -> p.name().length()).max().orElse(EMPTY_PLAYER.length());
        final int maxNumberOfCards = players.stream().map(Player::hands)
                                            .flatMap(Collection::stream)
                                            .mapToInt(Hand::numberOfCards)
                                            .max().orElse(0);

        final int maxLength;
        if (state == TableState.OPEN_TO_NEW_PLAYER) {
            maxLength = Math.max(EMPTY_PLAYER.length(), maxPlayerNameLength);
        } else {
            maxLength = maxPlayerNameLength;
        }

        final String playerFormat = String.format("%%2d. %%-%ds : ", maxLength);
        final String secondHeader = " ".repeat(2) + "  " + " ".repeat(maxLength) + " : ";

        for (int i = 0; i < tableSize; i++) {
            if (i < players.size()) {
                final Player player = players.get(i);
                final String firstHeader = String.format(playerFormat, i, player.name());
                final Printer printer = ps.withHeader(firstHeader, secondHeader);
                for (Hand hand : player.hands()) {
                    printer.println(String.format("(%2$3d) %1$s",hand.cardsAsString(),hand.betAmount()));
                }
            } else {
                ps.println(String.format(playerFormat, i, EMPTY_PLAYER));
            }
        }
        ps.println("────────────────");
        ps.withHead(" Dealer : ").println(dealerHand);

    }
}
