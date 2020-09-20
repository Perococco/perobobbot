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
    String nickOrServerName;

    String user;

    String host;

    @NonNull
    public Optional<String> user() {
        return Optional.ofNullable(user);
    }

    @NonNull
    public Optional<String> host() {
        return Optional.ofNullable(host);
    }
}
