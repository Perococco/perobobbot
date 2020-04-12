package perobobbot.bot.common.irc;

import bot.common.irc.IRCParser;
import bot.common.irc.IRCParsing;
import bot.common.irc.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class PerococcoIRCParser implements IRCParser {

    public static final Marker IRC_MARKER = MarkerManager.getMarker("IRC_PARSER");

    public static final String TAGS_PREFIX = "@";
    public static final String PREFIX_PREFIX = ":";

    private final TagParser tagParser = new TagParser();
    private final PrefixParser prefixParser = new PrefixParser();

    @Override
    public @NonNull IRCParsing parse(@NonNull String message) {
        return new Execution(new ParsedString(message)).parse();
    }

    @RequiredArgsConstructor
    private final class Execution {

        @NonNull
        private final ParsedString message;

        private final IRCParsing.Builder builder = IRCParsing.builder();

        public IRCParsing parse() {
            if (message.startsWith(TAGS_PREFIX)) {
                parseTags();
            }
            if (message.startsWith(PREFIX_PREFIX)) {
                parsePrefix();
            }

            parseCommand();
            parseParams();

            return builder.build();
        }


        private void parseTags() {
            final String tagsAsString = message.moveByStringLength(TAGS_PREFIX)
                                               .extractToNextSpace();
            final Map<String,Tag> tags = Stream.of(tagsAsString.split(";"))
                                               .map(tagParser::parse)
                                               .flatMap(Optional::stream)
                                               .filter(t -> !t.value().isEmpty())
                                               .collect(Collectors.toMap(Tag::keyName, t -> t));

            tags.forEach(builder::tag);
        }

        private void parsePrefix() {
            final String prefixAsString = message.moveByStringLength(PREFIX_PREFIX)
                                                 .extractToNextSpace();
            prefixParser.parse(prefixAsString).ifPresent(builder::prefix);
        }

        private void parseCommand() {
            builder.command(message.extractToNextSpace());
        }

        private void parseParams() {
            while (!message.isEmpty()) {
                if (message.startsWith(":")) {
                    builder.param(message.moveBy(1).extractToEndOfString());
                } else {
                    builder.param(message.extractToNextSpace());
                }
            }
        }

    }
}
