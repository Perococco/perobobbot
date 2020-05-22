package perococco.bot.blackjack;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perbobbot.blackjack.engine.Card;
import perococco.perobobbot.blackjack.engine.GetCardSymbol;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestGetCardSymbol {

    @NonNull
    public static Stream<Arguments> symbolSample() {
        return Stream.of(
                arguments(Card.ACE_OF_CLUBS, "\uD83C\uDCD1"),
                arguments(Card.ACE_OF_DIAMONDS, "\uD83C\uDCC1"),
                arguments(Card.ACE_OF_HEARTS, "\uD83C\uDCB1"),
                arguments(Card.ACE_OF_SPADES, "\uD83C\uDCA1"),
                arguments(Card.SEVEN_OF_SPADES, "\uD83C\uDCA7")
        );
    }

    @ParameterizedTest
    @MethodSource("symbolSample")
    public void shouldBeExpectedSymbol(@NonNull Card card, @NonNull String expected) {
        final String actual = GetCardSymbol.get(card.getFigure(),card.getSuit());
        final byte[] actualBytes = actual.getBytes(StandardCharsets.UTF_16);
        final byte[] expectedBytes = expected.getBytes(StandardCharsets.UTF_16);
        Assertions.assertArrayEquals(expectedBytes,actualBytes);
    }
}
