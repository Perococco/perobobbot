package bot.common.irc;

import lombok.*;

import java.util.Optional;

/**
 * @author perococco
 **/
@Value
@Builder(builderClassName = "Builder")
public class Tag {

    private final boolean client;

    @Getter(AccessLevel.NONE)
    private final String vendor;

    @NonNull
    private final String keyName;

    @NonNull
    private final String value;


    @NonNull
    public Optional<String> vendor() {
        return Optional.ofNullable(vendor);
    }
}
