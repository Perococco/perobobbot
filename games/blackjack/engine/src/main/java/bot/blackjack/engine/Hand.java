package bot.blackjack.engine;

import bot.common.lang.ListTool;
import bot.common.lang.fp.Couple;
import com.google.common.collect.ImmutableList;
import lombok.*;
import perococco.bot.blackjack.engine.HandValueComputer;

import java.util.Optional;

@ToString
public class Hand {

    /**
     * @return an empty hand for the dealer
     */
    @NonNull
    public static Hand forDealer() {
        return new Hand(ImmutableList.of(), -1, false, false);
    }

    /**
     * @return an empty hand for a player with the bet amount associated to it
     */
    @NonNull
    public static Hand forPlayer(int betAmount) {
        if (betAmount <= 0) {
            throw new IllegalArgumentException("Bet amount must be greater than 0: " + betAmount);
        }
        return new Hand(ImmutableList.of(), betAmount, false, false);
    }

    @Builder(toBuilder = true)
    private Hand(@NonNull @Singular ImmutableList<Card> cards, int betAmount, boolean fromASplit, boolean done) {
        this.cards = cards;
        this.betAmount = betAmount;
        this.fromASplit = fromASplit;
        this.value = HandValueComputer.compute(cards);
        final boolean twoCardFromASplitOfAce = fromASplit && cards.size() == 2 && cards.get(0).isAnAce();
        final boolean dealerWithMoreThan16 = betAmount<=0 && value>=17;
        this.done = done || value >= 21 || twoCardFromASplitOfAce || dealerWithMoreThan16;
    }

    /**
     * The cards composing this hand
     */
    @NonNull
    @Getter
    private final ImmutableList<Card> cards;

    /**
     * The bet amount associated with this hand
     */
    @Getter
    private final int betAmount;

    /**
     * if true, this hand has been obtained from a split
     */
    private final boolean fromASplit;

    /**
     * a flag indicating that this hand is done (after a stand, a double or a second card on a split ace)
     */
    @Getter
    private final boolean done;

    /**
     * The best value of this hand
     */
    @Getter
    private final int value;

    public int numberOfCards() {
        return cards.size();
    }

    @NonNull
    public Card cardAt(int index) {
        return cards.get(index);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean isBlackJack() {
        return value == 21 && cards.size() == 2 && !fromASplit;
    }

    public boolean isBusted() {
        return value > 21;
    }

    /**
     * @return true if this hand is the hand of the dealer
     */
    public boolean dealer() {
        return betAmount < 0;
    }

    /**
     * @return true if this hand has less than 2 cards
     */
    public boolean hasLessThanTwoCards() {
        return cards.size()<2;
    }

    /**
     * split this hand
     * @return an optional containing the list
     */
    @NonNull
    public Optional<Couple<Hand>> split() {
        if (done || dealer()) {
            return Optional.empty();
        }
        if (cards.size() == 2) {
            final Card first = cards.get(0);
            final Card second = cards.get(1);
            if (first.figure() == second.figure()) {
                final Hand firstHand = toBuilder().clearCards().card(first).fromASplit(true).build();
                final Hand secondHand = toBuilder().clearCards().card(second).fromASplit(true).build();
                return Optional.of(Couple.of(firstHand, secondHand));
            }
        }
        return Optional.empty();
    }

    @NonNull
    public Hand withSetDone() {
        if (done) {
            return this;
        }
        return toBuilder().done(true).build();
    }

    @NonNull
    public Hand addCard(@NonNull Card card) {
        return toBuilder().cards(ListTool.addLast(cards,card))
                          .build();
    }

    @NonNull
    public Optional<Hand> hitWith(@NonNull Card newCard) {
        if (done) {
            return Optional.empty();
        }
        final ImmutableList<Card> newCards = ListTool.addLast(cards, newCard);
        final Hand newHand = toBuilder().cards(newCards).build();
        return Optional.of(newHand);
    }

    @NonNull
    public Hand stand() {
        return withSetDone();
    }

    @NonNull
    public Optional<Hand> doubleWith(@NonNull Card newCard) {
        if (done || dealer() || cards.size() != 2) {
            return Optional.empty();
        }
        return Optional.of(toBuilder().cards(ListTool.addLast(cards, newCard))
                                      .betAmount(betAmount * 2)
                                      .done(true).build());
    }


}
