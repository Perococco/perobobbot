package bot.blackjack.engine.action;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.InvalidHandIndex;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.Table;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.CardToHandAdder;
import perococco.bot.blackjack.engine.PickResult;
import perococco.bot.blackjack.engine.TableHelper;

import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DealOneCardToPlayer {

    @NonNull
    public static Table execute(@NonNull Table table, @NonNull String playerName, int handIndex) {
        return new DealOneCardToPlayer(TableHelper.with(table), playerName, handIndex).execute();
    }

    private final TableHelper helper;

    @NonNull
    private final String playerName;

    private final int handIndex;

    private Player player;

    private PickResult pickResult;

    private ImmutableList<Hand> playerHands;

    @NonNull
    private Table execute() {
        this.getPlayerFromTable();
        this.checkHandIndexIsValid();
        this.pickOneCardFromTheDeck();

        if (player.hasNoCard()) {
            this.createInitialHand();
        } else {
            this.addPickedCardToHandAtGivenIndex();
        }

        final Player newPlayer = player.withNewHands(playerHands);
        return helper.replacePlayer(newPlayer, pickResult.deck());
    }

    private void getPlayerFromTable() {
        this.player = helper.getPlayerFromItsName(playerName);
    }

    private void checkHandIndexIsValid() {
        final boolean valid = (handIndex == 0 && player.hasNoCard())
                              || (handIndex < player.numberOfHands());
        if (!valid) {
            throw new InvalidHandIndex(playerName, handIndex);
        }
    }


    private void pickOneCardFromTheDeck() {
        this.pickResult = helper.pickFirstCardFromDeck();
    }

    private void createInitialHand() {
        playerHands = ImmutableList.of(Hand.of(pickResult.pickedCard()));
    }

    private void addPickedCardToHandAtGivenIndex() {
        playerHands = IntStream.range(0, player.numberOfHands())
                               .mapToObj(this::getHandAt)
                               .collect(ImmutableList.toImmutableList());
    }

    private Hand getHandAt(int index) {
        final Hand hand = player.handAt(index);
        if (index == handIndex) {
            return CardToHandAdder.add(pickResult.pickedCard())
                                  .to(hand);
        } else {
            return hand;
        }
    }


}
