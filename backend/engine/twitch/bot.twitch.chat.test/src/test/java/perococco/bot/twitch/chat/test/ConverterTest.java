package perococco.bot.twitch.chat.test;

import bot.twitch.chat.message.from.MessageFromTwitch;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import perococco.bot.twitch.chat.TwitchMessageConverter;

/**
 * @author perococco
 **/
public class ConverterTest {

    private static final TwitchMessageConverter CONVERTER = new TwitchMessageConverter();

    private static String[] chatSample() throws IOException {
        final URL url = ConverterTest.class.getResource("chat_2.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return reader.lines().toArray(String[]::new);
        }
    }

    @ParameterizedTest
    @MethodSource("chatSample")
    public void shouldParseMessageWithoutError(@NonNull String messageAsString) {
        final Optional<MessageFromTwitch> answer = CONVERTER.convert(messageAsString);
        Assertions.assertTrue(answer.isPresent());
    }

}
