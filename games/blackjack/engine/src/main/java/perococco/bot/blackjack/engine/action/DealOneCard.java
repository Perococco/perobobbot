package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.*;
import bot.common.lang.Mutation;
import lombok.NonNull;

public class DealOneCard implements Mutation<Table> {

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
                pickResult.deck(), handInfo.changeHand(h -> h.addCard(pickResult.pickedCard()))
        );
    }

    @NonNull
    private Table dealCardToDealer(@NonNull Table table) {
        final OnePickResult pickResult = table.pickOneCard();
        final Hand dealerHand = table.dealerHand().addCard(pickResult.pickedCard());
        return table.with(pickResult.deck(), dealerHand);
    }


}
