package perococco.perobobbot.common.irc;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.irc.Tag;
import perobobbot.lang.ThrowableTool;

import java.util.Optional;

import static perococco.perobobbot.common.irc.PerococcoIRCParser.IRC_MARKER;

/**
 * @author perococco
 **/
@Log4j2
public class TagParser {

    public static final String CLIENT_PREFIX = "+";

    private final TagValueUnescaper tagValueUnescaper = new TagValueUnescaper();

    @NonNull
    public Optional<Tag> parse(@NonNull String tagAsString) {
        try {
            return Optional.ofNullable(performParsing(tagAsString));
        } catch (Exception e) {
            LOG.warn(IRC_MARKER, "Fail to parse tag '" + tagAsString + "'", e);
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            return Optional.empty();
        }
    }

    private Tag performParsing(@NonNull String tagAsString) {
        final String[] token = tagAsString.split("=",2);
        if (token.length != 2 || token[0].isEmpty() || token[1].isBlank()) {
            return null;
        }
        final Tag.Builder builder = Tag.builder();
        final ParsedString key = new ParsedString(token[0]);
        final String value = tagValueUnescaper.unescape(token[1]);

        if (value.isEmpty()) {
            return null;
        }

        builder.value(value);

        if (key.startsWith(CLIENT_PREFIX)) {
            builder.client(true);
            key.moveBy(CLIENT_PREFIX.length());
        } else {
            builder.client(false);
        }

        final int lastSlashIndex = key.lastIndexOf("/");
        if (lastSlashIndex>=0) {
            builder.vendor(key.extractUpTo(lastSlashIndex));
            key.moveBy(1);
        }

        builder.keyName(key.extractToEndOfString());

        return builder.build();
    }
}
