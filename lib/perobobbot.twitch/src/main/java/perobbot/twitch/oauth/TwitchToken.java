package perobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.oauth.Token;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Value
public class TwitchToken {

    @JsonAlias("access_token") @NonNull String accessToken;
    @JsonAlias("refresh_token") @NonNull String refreshToken;
    @JsonAlias("expires_in") double expiresIn;
    @JsonAlias("scope") @NonNull String[] scopes;
    @JsonAlias("token_type") @NonNull String tokenType;

    public @NonNull Token toToken(@NonNull Instant now) {
        final var twitchScopes = Arrays.stream(scopes)
                                       .map(TwitchScope::findScopeByName)
                                       .flatMap(Optional::stream)
                                       .collect(ImmutableSet.toImmutableSet());

        final long duration = (long)expiresIn;

        return Token.builder()
                    .accessToken(accessToken)
                    .duration(duration)
                    .refreshToken(refreshToken)
                    .expirationInstant(now.plusSeconds(duration))
                    .scopes(twitchScopes)
                    .tokenType(tokenType)
                    .build();
    }


}
