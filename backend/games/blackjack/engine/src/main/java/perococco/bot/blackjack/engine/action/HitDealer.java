package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.OnePickResult;
import bot.blackjack.engine.Table;
import bot.blackjack.engine.TableState;
import bot.common.lang.Mutation;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HitDealer implements Mutation<Table> {

    @NonNull
    @Override
    public Table mutate(@NonNull Table table) {
        table.validateTableInState(TableState.DEALER_PHASE);

        final Hand dealerHand = table.getDealerHand();
        if (dealerHand.isDone()) {
            throw new IllegalStateException("[BUG] If deal hand is done, Table must be in GAME_OVER state.");
        }

        final OnePickResult pickResult = table.pickOneCard();

        final Hand newDealerHand = dealerHand.addCard(pickResult.getPickedCard());

        return table.with(pickResult.getDeck(),newDealerHand);
    }

}
