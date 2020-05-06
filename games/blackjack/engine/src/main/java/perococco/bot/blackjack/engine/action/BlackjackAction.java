package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlackjackAction {

    @NonNull
    @Getter(AccessLevel.PROTECTED)
    private final Table currentTable;

    protected void checkPlayerIsNotAlreadyAtTheTable(@NonNull Player player) {
        if (currentTable.hasPlayer(player.name())) {
            throw new PlayerAlreadyAtTable(player.name());
        }
    }

    protected void checkThatTheGameHasNotStartedYet(@NonNull String actionName) {
        if (currentTable.areAllHandsEmpty()) {
            return;
        }
        throw new GameAlreadyStarted(actionName);
    }

    @NonNull
    protected Deck currentDeck() {
        return currentTable.deck();
    }
}
