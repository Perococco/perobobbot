package bot.common.irc.test;

import bot.common.irc.IRCParser;
import bot.common.irc.IRCParsing;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author perococco
 **/
public class IRCParserRobustTest {

    private IRCParser ircParser = IRCParser.create();

    public static List<String> chatLines() throws IOException {
        final URL resource = IRCParserRobustTest.class.getResource("chat_1.txt");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(resource.openStream()))) {
            return r.lines().collect(Collectors.toList());
        }
    }


    @ParameterizedTest
    @MethodSource("chatLines")
    public void parseWithoutError(@NonNull String message) {
        final IRCParsing parsing = IRCParser.create().parse(message);
        Assertions.assertNotNull(parsing);
    }
}
