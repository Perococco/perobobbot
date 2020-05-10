package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.SingleHandInfo;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.exception.BlackjackException;
import bot.blackjack.engine.exception.InvalidHandForAStand;
import bot.common.lang.Mutation;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Stand implements Mutation<Table> {

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
