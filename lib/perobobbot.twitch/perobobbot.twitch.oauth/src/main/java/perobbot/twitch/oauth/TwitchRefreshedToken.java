package perobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.oauth.Token;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Value
public class TwitchRefreshedToken {

    @JsonAlias("access_token") @NonNull String accessToken;
    @JsonAlias("refresh_token") @NonNull String refreshToken;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scope") String[] scopes;

    public @NonNull Token update(@NonNull Token expiredToken) {
        return expiredToken.toBuilder()
                           .accessToken(this.accessToken)
                           .refreshToken(this.refreshToken)
                           .build();
    }


}
