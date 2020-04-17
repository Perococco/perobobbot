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

    @NonNull
    private final String rawMessage;

    @NonNull
    private final ImmutableMap<String,Tag> tags;

    @Getter(AccessLevel.NONE)
    private final Prefix prefix;

    @NonNull
    private final String command;

    @NonNull
    private final Params params;

    @NonNull
    public Optional<Prefix> prefix() {
        return Optional.ofNullable(prefix);
    }

    @NonNull
    public String lastParameter() {
        return params.lastParameter();
    }

    @NonNull
    public Stream<String> splitLastParameter(@NonNull String sep) {
        return Stream.of(lastParameter().split(sep));
    }

    @NonNull
    public Optional<String> tagValue(@NonNull String tagName) {
        return Optional.ofNullable(tags.get(tagName)).map(Tag::value);
    }
}
