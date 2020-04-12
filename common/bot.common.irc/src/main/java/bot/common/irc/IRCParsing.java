package bot.common.irc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.Optional;

/**
 * @author perococco
 **/
@Value
@Builder(builderClassName = "Builder")
public class IRCParsing {

    @NonNull
    private final String message;

    @NonNull
    @Singular
    private final ImmutableMap<String,Tag> tags;

    @Getter(AccessLevel.NONE)
    private final Prefix prefix;

    @NonNull
    private final String command;

    @NonNull
    @Singular
    private final ImmutableList<String> params;

    public Optional<Prefix> prefix() {
        return Optional.ofNullable(prefix);
    }
}
