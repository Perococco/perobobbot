package bot.common.irc;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
@Value
@Builder(builderClassName = "Builder")
public class IRCParsing {

    /**
     * The original message used by the parser
     */
    @NonNull
    private final String rawMessage;

    /**
     * A map of tags
     */
    @NonNull
    private final ImmutableMap<String,Tag> tags;

    /**
     * The optional prefix of the IRC message
     */
    @Getter(AccessLevel.NONE)
    private final Prefix prefix;

    /**
     * The command of the IRC message
     */
    @NonNull
    private final String command;

    /**
     * The parametres of the IRC message
     */
    @NonNull
    private final Params params;

    @NonNull
    public Optional<Prefix> prefix() {
        return Optional.ofNullable(prefix);
    }

    /**
     * @return the last parameter of the IRC message.
     * @throws IndexOutOfBoundsException if there is no such parameter
     */
    @NonNull
    public String lastParameter() {
        return params.lastParameter();
    }

    /**
     * The last parameter split in token
     * @param sep
     * @return
     */
    @NonNull
    public Stream<String> splitLastParameter(@NonNull String sep) {
        return Stream.of(lastParameter().split(sep));
    }

    @NonNull
    public Optional<String> tagValue(@NonNull String tagName) {
        return Optional.ofNullable(tags.get(tagName)).map(Tag::getValue);
    }
}