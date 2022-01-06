package perobobbot.twitch.oauth.impl;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;
import perobobbot.oauth.Token;
import perobobbot.twitch.oauth.api.TwitchScope;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Value
public class TwitchToken {

    @JsonAlias("access_token")
    @NonNull String accessToken;
    @JsonAlias("refresh_token")
    String refreshToken;
    @JsonAlias("expires_in")
    double expiresIn;
    @JsonAlias("scope")
    @Getter(AccessLevel.NONE)
    String[] scopes;
    @JsonAlias("token_type")
    @NonNull String tokenType;

    public @NonNull Token toToken(@NonNull Instant now) {

        final ImmutableSet<TwitchScope> twitchScopes;

        if (scopes == null) {
            twitchScopes = ImmutableSet.of();
        } else {
            twitchScopes = Arrays.stream(scopes)
                                 .map(TwitchScope::findScopeByName)
                                 .flatMap(Optional::stream)
                                 .collect(ImmutableSet.toImmutableSet());
        }

        final long duration = (long) expiresIn;

        return Token.builder()
                    .accessToken(Secret.with(accessToken))
                    .duration(duration)
                    .refreshToken(refreshToken == null?null:Secret.with(refreshToken))
                    .expirationInstant(now.plusSeconds(duration))
                    .scopes(twitchScopes)
                    .tokenType(tokenType)
                    .build();
    }

}
