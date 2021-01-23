package perobobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class AppAccessToken implements AccessToken {

    @JsonAlias("client_id")
    @NonNull String value;

    @NonNull Instant expirationTime;

    @NonNull ImmutableSet<Scope> scopes;

    @JsonAlias("response_type")
    @NonNull String type;

}
