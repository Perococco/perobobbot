package perobobbot.bot.common.irc;

import bot.common.irc.Prefix;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static perobobbot.bot.common.irc.PrefixParserTest.PrefixParserArgument.create;

/**
 * @author perococco
 **/
public class PrefixParserTest {


    public static Stream<PrefixParserArgument> validPrefixes() {
        return Stream.of(
                create("perococco.com","perococco.com",null,null),
                create("bronyark!bronyark@bronyark.tmi.twitch.tv","bronyark","bronyark","bronyark.tmi.twitch.tv"),
                create("name@host.com","name",null,"host.com"),
                create("name!user","name","user",null)

        );

    }

    private PrefixParser prefixParser;

    @BeforeEach
    void setUp() {
        this.prefixParser = new PrefixParser();
    }

    @ParameterizedTest
    @MethodSource("validPrefixes")
    public void hasRightNickOrPrefix(@NonNull PrefixParserArgument argument) {
        final Prefix prefix = parse(argument);
        Assertions.assertEquals(argument.prefix().nickOrServerName(),prefix.nickOrServerName());
    }



    private Prefix parse(@NonNull PrefixParserArgument argument) {
        final Prefix prefix = prefixParser.parse(argument.prefixAsString()).orElse(null);
        Assertions.assertNotNull(prefix);
        return prefix;
    }

    @Value
    public static class PrefixParserArgument {

        @NonNull
        public static PrefixParserArgument create(@NonNull String prefixAsString, @NonNull String nickOfServername,
                String user, String host) {
            return new PrefixParserArgument(
                    prefixAsString,
                    Prefix.builder()
                          .nickOrServerName(nickOfServername)
                          .user(user)
                          .host(host)
                          .build()
            );
        }

        private final String prefixAsString;

        @NonNull
        private final Prefix prefix;
    }


}
