package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.blackjack.engine.GetCardSymbol;

/**
 * Unicode symbol of cards https://en.wikipedia.org/wiki/Standard_52-card_deck#Unicode
 */
@RequiredArgsConstructor
public enum Card {

    KING_OF_SPADES(Figure.KING, Suit.SPADES),
    QUEEN_OF_SPADES(Figure.QUEEN, Suit.SPADES),
    JACK_OF_SPADES(Figure.JACK, Suit.SPADES),
    TEN_OF_SPADES(Figure.TEN, Suit.SPADES),
    NINE_OF_SPADES(Figure.NINE, Suit.SPADES),
    EIGHT_OF_SPADES(Figure.EIGHT, Suit.SPADES),
    SEVEN_OF_SPADES(Figure.SEVEN, Suit.SPADES),
    SIX_OF_SPADES(Figure.SIX, Suit.SPADES),
    FIVE_OF_SPADES(Figure.FIVE, Suit.SPADES),
    FOUR_OF_SPADES(Figure.FOUR, Suit.SPADES),
    THREE_OF_SPADES(Figure.THREE, Suit.SPADES),
    TWO_OF_SPADES(Figure.TWO, Suit.SPADES),
    ACE_OF_SPADES(Figure.ACE, Suit.SPADES),

    KING_OF_HEARTS(Figure.KING, Suit.HEARTS),
    QUEEN_OF_HEARTS(Figure.QUEEN, Suit.HEARTS),
    JACK_OF_HEARTS(Figure.JACK, Suit.HEARTS),
    TEN_OF_HEARTS(Figure.TEN, Suit.HEARTS),
    NINE_OF_HEARTS(Figure.NINE, Suit.HEARTS),
    EIGHT_OF_HEARTS(Figure.EIGHT, Suit.HEARTS),
    SEVEN_OF_HEARTS(Figure.SEVEN, Suit.HEARTS),
    SIX_OF_HEARTS(Figure.SIX, Suit.HEARTS),
    FIVE_OF_HEARTS(Figure.FIVE, Suit.HEARTS),
    FOUR_OF_HEARTS(Figure.FOUR, Suit.HEARTS),
    THREE_OF_HEARTS(Figure.THREE, Suit.HEARTS),
    TWO_OF_HEARTS(Figure.TWO, Suit.HEARTS),
    ACE_OF_HEARTS(Figure.ACE, Suit.HEARTS),

    KING_OF_DIAMONDS(Figure.KING, Suit.DIAMONDS),
    QUEEN_OF_DIAMONDS(Figure.QUEEN, Suit.DIAMONDS),
    JACK_OF_DIAMONDS(Figure.JACK, Suit.DIAMONDS),
    TEN_OF_DIAMONDS(Figure.TEN, Suit.DIAMONDS),
    NINE_OF_DIAMONDS(Figure.NINE, Suit.DIAMONDS),
    EIGHT_OF_DIAMONDS(Figure.EIGHT, Suit.DIAMONDS),
    SEVEN_OF_DIAMONDS(Figure.SEVEN, Suit.DIAMONDS),
    SIX_OF_DIAMONDS(Figure.SIX, Suit.DIAMONDS),
    FIVE_OF_DIAMONDS(Figure.FIVE, Suit.DIAMONDS),
    FOUR_OF_DIAMONDS(Figure.FOUR, Suit.DIAMONDS),
    THREE_OF_DIAMONDS(Figure.THREE, Suit.DIAMONDS),
    TWO_OF_DIAMONDS(Figure.TWO, Suit.DIAMONDS),
    ACE_OF_DIAMONDS(Figure.ACE, Suit.DIAMONDS),

    KING_OF_CLUBS(Figure.KING, Suit.CLUBS),
    QUEEN_OF_CLUBS(Figure.QUEEN, Suit.CLUBS),
    JACK_OF_CLUBS(Figure.JACK, Suit.CLUBS),
    TEN_OF_CLUBS(Figure.TEN, Suit.CLUBS),
    NINE_OF_CLUBS(Figure.NINE, Suit.CLUBS),
    EIGHT_OF_CLUBS(Figure.EIGHT, Suit.CLUBS),
    SEVEN_OF_CLUBS(Figure.SEVEN, Suit.CLUBS),
    SIX_OF_CLUBS(Figure.SIX, Suit.CLUBS),
    FIVE_OF_CLUBS(Figure.FIVE, Suit.CLUBS),
    FOUR_OF_CLUBS(Figure.FOUR, Suit.CLUBS),
    THREE_OF_CLUBS(Figure.THREE, Suit.CLUBS),
    TWO_OF_CLUBS(Figure.TWO, Suit.CLUBS),
    ACE_OF_CLUBS(Figure.ACE, Suit.CLUBS),

    ;

    @NonNull
    @Getter
    private final Figure figure;

    @NonNull
    @Getter
    private final Suit suit;

    @NonNull
    @Getter
    private final String symbol;

    Card(@NonNull Figure figure, @NonNull Suit suit) {
        this.figure = figure;
        this.suit = suit;
        this.symbol = GetCardSymbol.get(figure,suit);
    }

    @Override
    public String toString() {
        return symbol;
    }

    @NonNull
    public static ImmutableList<Card> allCards() {
        return Holder.ALL_CARDS;
    }

    public boolean isAnAce() {
        return figure.isAnAce();
    }


    private static class Holder {
        private static final ImmutableList<Card> ALL_CARDS = ImmutableList.copyOf(values());

    }

}
