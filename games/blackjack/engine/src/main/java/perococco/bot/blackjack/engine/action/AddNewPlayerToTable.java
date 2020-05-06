package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.BlackjackTable;
import bot.blackjack.engine.Player;
import bot.common.lang.MapTool;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public class AddNewPlayerToTable extends BlackjackAction {

    @NonNull
    public static BlackjackTable addPlayer(@NonNull BlackjackTable currentTable, @NonNull Player player) {
        return new AddNewPlayerToTable(currentTable,player).mutate();
    }

    @NonNull
    private final Player player;

    private AddNewPlayerToTable(@NonNull BlackjackTable currentTable, @NonNull Player player) {
        super(currentTable);
        this.player = player;
    }

    private ImmutableMap<String,Player> newPlayerMap;

    private BlackjackTable newTable;

    @NonNull
    private BlackjackTable mutate() {
        this.checkPlayerIsNotAlreadyAtTheTable(player);
        this.checkThatTheGameHasNotStartedYet("Add a player");

        this.createNewPlayerMap();
        this.createNewTable();

        return newTable;
    }

    private void createNewPlayerMap() {
        this.newPlayerMap = MapTool.add(currentTable().players(), player.name(), player);
    }

    private void createNewTable() {
        newTable = currentTable().toBuilder().players(newPlayerMap).build();
    }

}
