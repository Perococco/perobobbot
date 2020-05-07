package bot.blackjack.engine.action;

import bot.blackjack.engine.*;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.TableHelper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SplitHand {

    @NonNull
    public static Table execute(@NonNull Table table, @NonNull String playerName) {
        return new SplitHand(TableHelper.with(table), playerName).execute();
    }

    @NonNull
    private final TableHelper helper;

    @NonNull
    private final String playerName;


    private Player player;

    private Card firstAce;

    private Card secondAce;

    private ImmutableList<Hand> splitHands;

    @NonNull
    private Table execute() {
        this.getPlayerFromItsName();
        this.extractTheTwoAcesToSplit();
        this.createSplitHand();

        final Player newPlayer = player.withNewHands(splitHands);
        return helper.replacePlayer(newPlayer);
    }

    private void getPlayerFromItsName() {
        player = helper.getPlayerFromItsName(playerName);
    }

    private void extractTheTwoAcesToSplit() {
        if (player.hasTwoCardsInOneHand()) {
            final Hand hand = player.handAt(0);
            firstAce = hand.cardAt(0);
            secondAce = hand.cardAt(1);
            if (firstAce.isAnAce() && secondAce.isAnAce()) {
                return;
            }
        }
        throw new InvalidHandForASplit(playerName,player.hands());
    }

    private void createSplitHand() {
        splitHands = ImmutableList.of(Hand.of(firstAce), Hand.of(secondAce));
    }


}
