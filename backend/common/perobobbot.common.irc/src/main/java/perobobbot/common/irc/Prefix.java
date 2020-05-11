package perobobbot.common.irc;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

/**
 * @author perococco
 **/
@Value
@Builder(builderClassName = "Builder")
public class Prefix {

    @NonNull
    private final String nickOrServerName;

    private final String user;

    private final String host;

    public Optional<String> user() {
        return Optional.ofNullable(user);
    }

    public Optional<String> host() {
        return Optional.ofNullable(host);
    }
}
