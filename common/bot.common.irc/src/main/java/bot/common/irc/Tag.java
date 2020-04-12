package bot.common.irc;

import lombok.*;

import java.util.Optional;

/**
 * @author perococco
 **/
@Value
@Builder(builderClassName = "Builder")
public class Tag {

    @Getter(AccessLevel.NONE)
    private final boolean client;

    @Getter(AccessLevel.NONE)
    private final String vendor;

    @NonNull
    private final String keyName;

    @NonNull
    private final String value;

    public boolean client() {
        return client;
    }

    @NonNull
    public Optional<String> vendor() {
        return Optional.ofNullable(vendor);
    }
}
