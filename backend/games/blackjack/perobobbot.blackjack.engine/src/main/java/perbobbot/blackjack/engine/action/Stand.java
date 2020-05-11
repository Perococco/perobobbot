package perbobbot.blackjack.engine.action;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perbobbot.blackjack.engine.Hand;
import perbobbot.blackjack.engine.SingleHandInfo;
import perbobbot.blackjack.engine.Table;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Stand extends BlackjackAction {

    @NonNull
    public static Stand with(String playerName) {
        return new Stand(playerName);
    }

    @NonNull
    private final String playerName;

    @NonNull
    @Override
    public Table mutate(@NonNull Table table) {
        return table.findFirstHandNotDoneOnTable()
                    .filter(h -> h.playerHasName(playerName))
                    .map(h -> performAction(table, h))
                    .orElse(table);
    }

    private @NonNull Table performAction(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        if (handInfo.hand().isDone()) {
            return table;
        }
        return table.withReplacedHand(
                table.getDeck(),
                handInfo.changeHand(Hand::withSetDone)
        );
    }

}
