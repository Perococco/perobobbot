package perobobbot.discord.resources;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.discord.oauth.api.DiscordScope;
import perobobbot.lang.Secret;
import perobobbot.oauth.Token;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Value
public class DiscordToken {

    @JsonAlias("access_token")
    @NonNull String accessToken;
    @JsonAlias("refresh_token")
    String refreshToken;
    @JsonAlias("expires_in")
    double expiresIn;
    @Getter(AccessLevel.NONE)
    String scope;
    @JsonAlias("token_type")
    @NonNull String tokenType;

    public @NonNull Token toToken(@NonNull Instant now) {

        final ImmutableSet<DiscordScope> discordScopes;

        if (scope == null) {
            discordScopes = ImmutableSet.of();
        } else {
            discordScopes = Arrays.stream(scope.split(" "))
                                 .map(DiscordScope::findScopeByName)
                                 .flatMap(Optional::stream)
                                 .collect(ImmutableSet.toImmutableSet());
        }

        final long duration = (long) expiresIn;

        return Token.builder()
                    .accessToken(Secret.with(accessToken))
                    .duration(duration)
                    .refreshToken(refreshToken == null?null:Secret.with(refreshToken))
                    .expirationInstant(now.plusSeconds(duration))
                    .scopes(discordScopes)
                    .tokenType(tokenType)
                    .build();
    }

}
