package perococco.bot.blackjack.engine;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import bot.common.lang.Printer;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class TablePrinter {

    public static final String EMPTY_PLAYER = "Empty";

    public static void print(@NonNull Table table, @NonNull Printer printer) {
        new TablePrinter(table,printer).print();
    }


    @NonNull
    private final Table table;

    private final Printer ps;

    private final TableState tableState;

    private final ImmutableList<Player> players;

    private final Hand dealerHand;

    String firstHeadFormat;

    String secondHeader;

    boolean notDoneFound = false;


    public TablePrinter(@NonNull Table table, Printer ps) {
        this.table = table;
        this.ps = ps;
        this.tableState = table.getState();
        this.players = table.getPlayers();
        this.dealerHand = table.getDealerHand();
    }

    public void print() {
        printHeader();
        ps.println("────────────────");

        preparePlayerHeaders();
        printPlayers();

        ps.println("────────────────");

        printDealerHand();
    }


    private void printHeader() {
        ps.println(table.getState()+" ["+table.getGeneration()+"]");
    }


    private void preparePlayerHeaders() {
        final int maxPlayerNameLength = computeMaxPlayerNameLength();
        this.firstHeadFormat = String.format(" %%-%ds : ", maxPlayerNameLength);
        this.secondHeader = " " + " ".repeat(maxPlayerNameLength) + " : ";
    }

    private void printPlayers() {
        final int nbToList = tableState == TableState.OPEN_TO_NEW_PLAYER ? table.size() : players.size();
        for (int i = 0; i < nbToList; i++) {
            if (i < players.size()) {
                final Player player = players.get(i);
                final String firstHeader = String.format(firstHeadFormat, player.getName());
                final Printer printer = ps.withHeader(firstHeader, secondHeader);
                printPlayerHands(player,printer);
            } else {
                ps.println(String.format(firstHeadFormat, i, EMPTY_PLAYER));
            }
        }
    }

    private void printPlayerHands(@NonNull Player player, @NonNull Printer printer) {
        for (Hand hand : player.getHands()) {
            String flag = getHandFlag(hand);
            printer.println(String.format("(%3d) %1s %s", hand.getBetAmount(), flag, hand.cardsAsString()));
        }
    }

    private String getHandFlag(@NonNull Hand hand) {
        final String flag;
        if (tableState == TableState.GAME_OVER) {
            flag = hand.getStatus(dealerHand);
        } else if (tableState != TableState.PLAYER_PHASE) {
            flag = " ";
        } else if (hand.isDone()) {
            flag = "-";
        } else if (notDoneFound) {
            flag = " ";
        } else {
            notDoneFound = true;
            flag = "*";
        }
        return flag;

    }

    private int computeMaxPlayerNameLength() {
        int maxPlayerNameLength = table.getPlayers()
                                       .stream()
                                       .mapToInt(this::playerNameLength)
                                       .max()
                                       .orElse(EMPTY_PLAYER.length());

        final int maxLength;
        if (table.getState() == TableState.OPEN_TO_NEW_PLAYER) {
            maxLength = Math.max(EMPTY_PLAYER.length(), maxPlayerNameLength);
        } else {
            maxLength = maxPlayerNameLength;
        }

        return maxLength;
    }

    private int playerNameLength(@NonNull Player player) {
        return player.getName().length();
    }

    private void printDealerHand() {
        ps.withHead(" Dealer : ").println(dealerHandAsString());
    }

    private String dealerHandAsString() {
        final String dh;
        if (dealerHand.isBlackJack() || tableState == TableState.DEALER_PHASE || tableState == TableState.GAME_OVER) {
            dh = dealerHand.toString();
        } else {
            dh = dealerHand.displayOnlyFirst();
        }
        return dh;
    }
}
