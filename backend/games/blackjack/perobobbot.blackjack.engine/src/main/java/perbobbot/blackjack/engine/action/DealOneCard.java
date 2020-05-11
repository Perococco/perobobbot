package perbobbot.blackjack.engine.action;

import lombok.NonNull;
import perbobbot.blackjack.engine.*;

public class DealOneCard extends BlackjackAction {

    private static final DealOneCard INSTANCE = new DealOneCard();

    @NonNull
    public static DealOneCard create() {
        return INSTANCE;
    }

    @Override
    public @NonNull Table mutate(@NonNull Table helper) {
        helper.validateTableInState(TableState.DEALING);
        return helper.findFirstPlayerHandToDealTo()
                     .map(handInfo -> dealCardToPlayer(helper, handInfo))
                     .orElseGet(() -> dealCardToDealer(helper));
    }


    @NonNull
    private Table dealCardToPlayer(@NonNull Table table, @NonNull SingleHandInfo handInfo) {
        final OnePickResult pickResult = table.pickOneCard();

        return table.withReplacedHand(
                pickResult.getDeck(), handInfo.changeHand(h -> h.addCard(pickResult.getPickedCard()))
        );
    }

    @NonNull
    private Table dealCardToDealer(@NonNull Table table) {
        final OnePickResult pickResult = table.pickOneCard();
        final Hand dealerHand = table.getDealerHand().addCard(pickResult.getPickedCard());
        return table.with(pickResult.getDeck(), dealerHand);
    }


}
