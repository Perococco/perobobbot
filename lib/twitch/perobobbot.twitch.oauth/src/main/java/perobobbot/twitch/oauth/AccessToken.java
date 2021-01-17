package perobobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.time.Instant;

public interface AccessToken {

    @NonNull String getValue();
    @NonNull Instant getExpirationTime();
    @NonNull ImmutableSet<Scope> getScopes();
    @NonNull String getType();
}
