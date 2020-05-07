package bot.blackjack.engine.action;

import bot.blackjack.engine.Player;
import bot.blackjack.engine.Table;
import bot.common.lang.ListTool;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.TableHelper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPlayerToTable {

    @NonNull
    public static Table execute(@NonNull Table table, @NonNull Player newPlayer) {
        return new AddPlayerToTable(TableHelper.with(table), newPlayer).execute();
    }

    @NonNull
    private final TableHelper helper;

    @NonNull
    private final Player newPlayer;

    private ImmutableList<Player> newPlayers;

    private Table newTable;

    @NonNull
    private Table execute() {
        helper.checkThatTableIsOpenedToNewPlayer();
        helper.checkPlayerIsNotAlreadyAtTheTable(newPlayer);
        this.createNewPlayerList();
        this.buildNewTableWithNewPlayers();

        return newTable;
    }

    private void buildNewTableWithNewPlayers() {
        newTable = helper.toBuilder()
                   .players(newPlayers)
                   .build();
    }

    private void createNewPlayerList() {
        this.newPlayers = ListTool.addLast(helper.players(), newPlayer);
    }


}
