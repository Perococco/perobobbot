package perococco.perobobbot.common.irc;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import perobobbot.common.irc.*;
import perobobbot.common.lang.MapTool;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class PerococcoIRCParser implements IRCParser {

    public static final Marker IRC_MARKER = MarkerManager.getMarker("IRC_PARSER");

    public static final String TAGS_PREFIX = "@";
    public static final String PREFIX_PREFIX = ":";
    public static final String LAST_PARAMETER_PREFIX = ":";

    private final TagParser tagParser = new TagParser();
    private final PrefixParser prefixParser = new PrefixParser();

    @Override
    public @NonNull IRCParsing parse(@NonNull String message) {
        return new Execution(message).parse();
    }

    @RequiredArgsConstructor
    private final class Execution {

        @NonNull
        private final String rawMessage;

        private Prefix prefix;

        private ImmutableMap<String, Tag> tags = ImmutableMap.of();

        private String command;

        private Params params;

        public IRCParsing parse() {
            final ParsedString parsedString = new ParsedString(rawMessage);

            parsedString.extractToNextSpaceIfStartWith(TAGS_PREFIX).ifPresent(this::parseTags);
            parsedString.extractToNextSpaceIfStartWith(PREFIX_PREFIX).ifPresent(this::parsePrefix);

            parseCommand(parsedString);
            parseParams(parsedString);

            return IRCParsing.builder()
                             .rawMessage(rawMessage)
                             .prefix(prefix)
                             .tags(tags)
                             .params(params)
                             .command(command)
                             .build();
        }


        private void parseTags(@NonNull String tagsAsString) {
            tags = Stream.of(tagsAsString.split(";"))
                         .map(tagParser::parse)
                         .flatMap(Optional::stream)
                         .filter(t -> !t.getValue().isEmpty())
                         .collect(MapTool.collector(Tag::getKeyName));
        }

        private void parsePrefix(@NonNull String prefixAsString) {
            prefix = prefixParser.parse(prefixAsString).orElse(null);
        }

        private void parseCommand(@NonNull ParsedString parsedString) {
            command = parsedString.extractToNextSpaceOrEndOfString();
        }

        private void parseParams(@NonNull ParsedString parsedString) {
            final Params.Builder builder = Params.builder();

            while (!parsedString.isEmpty()) {
                if (parsedString.startsWith(LAST_PARAMETER_PREFIX)) {
                    builder.parameter(parsedString.moveByStringLength(LAST_PARAMETER_PREFIX).extractToEndOfString());
                } else {
                    builder.parameter(parsedString.extractToNextSpaceOrEndOfString());
                }
            }

            params = builder.build();
        }

    }
}
