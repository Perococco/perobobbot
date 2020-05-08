package perococco.bot.blackjack;

import bot.blackjack.engine.Card;
import bot.blackjack.engine.Figure;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perococco.bot.blackjack.engine.HandValueComputer;
import perococco.bot.blackjack.engine.action.Stand;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HandValueComputerTest {


    public static Stream<Arguments> handList() {
        return Stream.of(
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.KING_OF_CLUBS), 21),
                arguments(__(Card.ACE_OF_CLUBS, Card.TEN_OF_CLUBS), 21),
                arguments(__(Card.TEN_OF_CLUBS, Card.TEN_OF_CLUBS), 20),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS), 13),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS), 15),

                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.KING_OF_CLUBS), 15),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.QUEEN_OF_CLUBS), 15),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.JACK_OF_CLUBS), 15),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.TEN_OF_CLUBS), 15),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.NINE_OF_CLUBS), 14),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.EIGHT_OF_CLUBS), 13),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.SEVEN_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.SIX_OF_CLUBS), 21),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.FIVE_OF_CLUBS), 20),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.FOUR_OF_CLUBS), 19),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.THREE_OF_CLUBS), 18),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.TWO_OF_CLUBS), 17),
                arguments(__(Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS, Card.ACE_OF_CLUBS), 16),

                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.KING_OF_CLUBS), 14),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.QUEEN_OF_CLUBS), 14),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.JACK_OF_CLUBS), 14),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.TEN_OF_CLUBS), 14),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.NINE_OF_CLUBS), 13),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.EIGHT_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.SEVEN_OF_CLUBS), 21),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.SIX_OF_CLUBS), 20),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.FIVE_OF_CLUBS), 19),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.FOUR_OF_CLUBS), 18),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.THREE_OF_CLUBS), 17),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.TWO_OF_CLUBS), 16),
                arguments(__(Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS, Card.ACE_OF_CLUBS), 15),

                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.KING_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.QUEEN_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.JACK_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.TEN_OF_CLUBS), 12),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.NINE_OF_CLUBS), 21),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.EIGHT_OF_CLUBS), 20),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.SEVEN_OF_CLUBS), 19),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.SIX_OF_CLUBS), 18),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.FIVE_OF_CLUBS), 17),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.FOUR_OF_CLUBS), 16),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.THREE_OF_CLUBS), 15),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.TWO_OF_CLUBS), 14),
                arguments(__(Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS, Card.ACE_OF_CLUBS), 13),

                arguments(__(Card.ACE_OF_CLUBS, 1), 11),
                arguments(__(Card.ACE_OF_CLUBS, 2), 12),
                arguments(__(Card.ACE_OF_CLUBS, 3), 13),
                arguments(__(Card.ACE_OF_CLUBS, 4), 14),
                arguments(__(Card.ACE_OF_CLUBS, 5), 15),
                arguments(__(Card.ACE_OF_CLUBS, 6), 16),
                arguments(__(Card.ACE_OF_CLUBS, 7), 17),
                arguments(__(Card.ACE_OF_CLUBS, 8), 18),
                arguments(__(Card.ACE_OF_CLUBS, 9), 19),
                arguments(__(Card.ACE_OF_CLUBS, 10), 20),
                arguments(__(Card.ACE_OF_CLUBS, 11), 21),
                arguments(__(Card.ACE_OF_CLUBS, 12), 12),
                arguments(__(Card.ACE_OF_CLUBS, 13), 13)



        );
    }

    @ParameterizedTest
    @MethodSource("handList")
    public void shouldHaveExpectedValue(@NonNull ImmutableList<Card> cards, int value) {
        final int actual = HandValueComputer.compute(cards);
        Assertions.assertEquals(value,actual);
    }

    @NonNull
    private static ImmutableList<Card> __(@NonNull Card...cards) {
        return ImmutableList.copyOf(cards);
    }

    @NonNull
    private static ImmutableList<Card> __(@NonNull Card card, int nbTimes) {
        return IntStream.range(0,nbTimes).mapToObj(i -> card).collect(ImmutableList.toImmutableList());
    }


}
