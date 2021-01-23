package perococco.perobobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.oauth.AppAccessToken;
import perobobbot.twitch.oauth.Scope;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Value
public class JsonClientCredential {

    @JsonAlias("access_token")
    @NonNull String accessToken;
    @JsonAlias("expires_in")
    @NonNull int expiresIn;
    @JsonAlias("scope")
    @NonNull Set<String> scopes;
    @JsonAlias("token_type")
    @NonNull String tokenType;

    public @NonNull AppAccessToken toAppAccessToken(@NonNull Instant now) {
        return AppAccessToken.builder()
                             .value(accessToken)
                             .expirationTime(now.plusSeconds(expiresIn))
                             .scopes(scopes.stream().map(Scope::findById).flatMap(Optional::stream).collect(ImmutableSet.toImmutableSet()))
                             .type(tokenType)
                             .build();
    }
}
