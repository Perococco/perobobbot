package perococco.bot.blackjack.engine;

import bot.blackjack.engine.BlackjackTable;
import bot.blackjack.engine.GameAlreadyStarted;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.PlayerAlreadyAtTable;
import bot.common.lang.MapTool;
import bot.common.lang.Mutation;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AddNewPlayerToTable {

    @NonNull
    public static Mutation<BlackjackTable> createMutation(@NonNull String playerName, int betAmount) {
        return state -> addPlayer(state, playerName, betAmount);
    }

    @NonNull
    public static BlackjackTable addPlayer(@NonNull BlackjackTable currentTable, @NonNull String playerName, int betAmount) {
        return new AddNewPlayerToTable(currentTable,playerName,betAmount).mutate();
    }

    @NonNull
    private final BlackjackTable currentTable;

    @NonNull
    private final String playerName;

    private final int betAmount;

    @NonNull
    private BlackjackTable mutate() {
        this.checkUserIsNotAlreadyAtTheTable();
        this.checkThatTheGameDidNotStart();

        final ImmutableMap<String,Player> newPlayers = MapTool.add(currentTable.players(), playerName, Player.create(playerName, betAmount));
        return currentTable.toBuilder()
                           .players(newPlayers)
                           .build();
    }

    private void checkUserIsNotAlreadyAtTheTable() {
        if (currentTable.hasPlayer(playerName)) {
            throw new PlayerAlreadyAtTable(playerName);
        }
    }

    private void checkThatTheGameDidNotStart() {
        if (currentTable.areAllHandsEmpty()) {
            return;
        }
        throw new GameAlreadyStarted("Add a new player");
    }
}
