package bot.blackjack.engine.action;

import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EndGame {

    @NonNull
    public static Table execute(@NonNull Table table) {
        return new EndGame(table).execute();
    }

    @NonNull
    private final Table table;

    @NonNull
    private Table execute() {
        return table.toBuilder().state(TableState.GAME_OVER).build();
    }
}
