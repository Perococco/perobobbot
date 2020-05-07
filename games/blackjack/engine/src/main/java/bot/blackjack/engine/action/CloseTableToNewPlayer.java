package bot.blackjack.engine.action;

import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.TableHelper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CloseTableToNewPlayer {

    @NonNull
    public static Table execute(@NonNull Table table) {
        return new CloseTableToNewPlayer(TableHelper.with(table)).execute();
    }

    private final @NonNull TableHelper helper;

    @NonNull
    private Table execute() {
        helper.checkThatTableIsOpenedToNewPlayer();
        return helper.toBuilder()
                     .state(TableState.GAME_IN_PROGRESS)
                     .build();
    }
}
