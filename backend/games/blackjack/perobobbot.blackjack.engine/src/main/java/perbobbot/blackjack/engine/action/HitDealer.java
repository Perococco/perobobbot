package perbobbot.blackjack.engine.action;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perbobbot.blackjack.engine.Hand;
import perbobbot.blackjack.engine.OnePickResult;
import perbobbot.blackjack.engine.Table;
import perbobbot.blackjack.engine.TableState;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HitDealer extends BlackjackAction {

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
