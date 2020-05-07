package perococco.bot.blackjack.engine;

import bot.blackjack.engine.*;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TableHelper {

    @NonNull
    public static TableHelper with(Table table) {
        return new TableHelper(table);
    }


    @NonNull
    private final Table table;

    public void checkPlayerIsNotAlreadyAtTheTable(@NonNull Player player) {
        if (table.hasPlayer(player.name())) {
            throw new PlayerAlreadyAtTable(player.name());
        }
    }

    public void checkTableState(@NonNull TableState expectedState) {
        if (tableState() != expectedState) {
            throw new InvalidTableState(expectedState, tableState());
        }
    }

    public void checkThatGameIsInProgress() {
        this.checkTableState(TableState.GAME_IN_PROGRESS);
    }

    public void checkThatTableIsOpenedToNewPlayer() {
        this.checkTableState(TableState.OPEN_TO_NEW_PLAYER);
    }

    @NonNull
    public Player getPlayerFromItsName(@NonNull String playerName) {
        return table.getPlayer(playerName);
    }

    public PickResult pickFirstCardFromDeck() {
        return PickFirstCardFromDeck.pickCards(table.deck());
    }

    @NonNull
    public ImmutableList<Player> players() {
        return table.players();
    }

    @NonNull
    public Table replacePlayer(@NonNull Player newPlayer, @NonNull Deck newDeck) {
        final ImmutableList<Player> players = table.players().stream()
                .map(p -> p.hasName(newPlayer.name())?newPlayer:p)
                .collect(ImmutableList.toImmutableList());
        return toBuilder().players(players).deck(newDeck).build();
    }


    @NonNull
    public Table replacePlayer(@NonNull Player newPlayer) {
        return replacePlayer(newPlayer,table.deck());
    }

    @NonNull
    public Table.TableBuilder toBuilder() {
        return table.toBuilder();
    }

    @NonNull
    public Hand dealerHand() {
        return table.dealerHand();
    }

    @NonNull
    public TableState tableState() {
        return table.state();
    }

    public void checkThatDealerInNotAt17OrMore() {
        if (dealerHand().value() >= 17) {
            throw new DealToDealerFailed(dealerHand().value());
        }
    }
}
