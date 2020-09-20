package perbobbot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import perococco.perobobbot.blackjack.engine.HandValueComputer;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Builder(toBuilder = true)
    private Hand(@NonNull @Singular ImmutableList<Card> cards, int betAmount, boolean fromASplit, boolean done) {
        this.cards = cards;
        this.betAmount = betAmount;
        this.fromASplit = fromASplit;
        this.value = HandValueComputer.compute(cards);
        final boolean twoCardFromASplitOfAce = fromASplit && cards.size() == 2 && cards.get(0).isAnAce();
        final boolean dealerWithMoreThan16 = betAmount <= 0 && value >= 17;
        this.done = done || value > 21 || twoCardFromASplitOfAce || dealerWithMoreThan16;
    }


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
    public boolean isDealerHand() {
        return betAmount < 0;
    }

    /**
     * @return true if this hand has less than 2 cards
     */
    public boolean hasLessThanTwoCards() {
        return cards.size() < 2;
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
        return toBuilder()
                .card(card)
                .build();
    }

    @NonNull
    public String cardsAsString() {
        return cards.stream()
                    .map(Card::getSymbol)
                    .collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        final String suffix;
        if (isDealerHand()) {
            suffix = "";
        } else {
            suffix = "(" + betAmount + ")";
        }
        return cardsAsString() + suffix;
    }

    @NonNull
    public String displayOnlyFirst() {
        if (cards.isEmpty()) {
            return "";
        }
        return IntStream.range(0, cards.size())
                 .mapToObj(i -> (i == 0) ? cards.get(i).getSymbol() : "\uD83C\uDCA0")
                 .collect(Collectors.joining(" "));
    }

    @NonNull
    public String getStatus(Hand dealerHand) {
        if (isBusted()) {
            return "*";
        } else if (isBlackJack() && !dealerHand.isBlackJack()) {
            return "\uD83D\uDC4C";
        } else if (value > dealerHand.value) {
            return "\uD83D\uDC4D";
        } else if (value == dealerHand.value) {
            return "=";
        } else {
            return "\uD83D\uDC4E";
        }
    }
}
