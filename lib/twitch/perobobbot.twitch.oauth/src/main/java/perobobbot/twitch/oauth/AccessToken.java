package perobobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Value
@Builder
public class AccessToken {

    @NonNull String value;
    @Getter(AccessLevel.NONE)
    String refresh;
    @NonNull Instant expirationTime;
    @NonNull ImmutableSet<Scope> scopes;
    @NonNull String type;

    public @NonNull Optional<String> getRefreshToken() {
        return Optional.ofNullable(refresh);
    }
}
