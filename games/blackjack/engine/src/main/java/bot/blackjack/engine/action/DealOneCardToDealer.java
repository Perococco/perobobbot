package bot.blackjack.engine.action;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.Table;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.CardToHandAdder;
import perococco.bot.blackjack.engine.PickResult;
import perococco.bot.blackjack.engine.TableHelper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DealOneCardToDealer {

    @NonNull
    public static Table execute(@NonNull Table table) {
        return new DealOneCardToDealer(TableHelper.with(table)).execute();
    }

    private final @NonNull TableHelper helper;

    @NonNull
    private Table execute() {
        helper.checkThatGameIsInProgress();
        helper.checkThatDealerInNotAt17OrMore();

        final PickResult pickResult = helper.pickFirstCardFromDeck();

        final Hand newDealerHand = CardToHandAdder.add(pickResult.pickedCard())
                                                  .to(helper.dealerHand());

        return helper.toBuilder()
                     .deck(pickResult.deck())
                     .dealerHand(newDealerHand)
                     .build();

    }
}
