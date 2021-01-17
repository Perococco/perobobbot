package perobobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class UserAccessToken implements AccessToken {

    @NonNull String value;

    @NonNull Instant expirationTime;

    @NonNull ImmutableSet<Scope> scopes;

    @NonNull String type;

}
