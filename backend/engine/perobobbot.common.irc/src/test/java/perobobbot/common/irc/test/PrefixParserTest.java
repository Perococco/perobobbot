package perobobbot.common.irc.test;

import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.common.irc.Prefix;
import perococco.perobobbot.common.irc.PrefixParser;

import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class PrefixParserTest {


    public static Stream<PrefixParserArgument> validPrefixes() {
        return Stream.of(
                PrefixParserArgument.create("perococco.com", "perococco.com", null, null),
                PrefixParserArgument.create("bronyark!bronyark@bronyark.tmi.twitch.tv", "bronyark", "bronyark", "bronyark.tmi.twitch.tv"),
                PrefixParserArgument.create("name@host.com", "name", null, "host.com"),
                PrefixParserArgument.create("name!user", "name", "user", null)

        );

    }

    private PrefixParser prefixParser;

    @BeforeEach
    public void setUp() {
        this.prefixParser = new PrefixParser();
    }

    @ParameterizedTest
    @MethodSource("validPrefixes")
    public void hasRightNickOrPrefix(@NonNull PrefixParserArgument argument) {
        final Prefix prefix = parse(argument);
        Assertions.assertEquals(argument.getPrefix().getNickOrServerName(),prefix.getNickOrServerName());
    }



    private Prefix parse(@NonNull PrefixParserArgument argument) {
        final Prefix prefix = prefixParser.parse(argument.getPrefixAsString()).orElse(null);
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
