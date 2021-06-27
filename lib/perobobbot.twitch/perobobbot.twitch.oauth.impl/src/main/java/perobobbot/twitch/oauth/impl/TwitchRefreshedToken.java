package perobobbot.twitch.oauth.impl;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;
import perobobbot.oauth.RefreshedToken;
import perobobbot.oauth.Token;

@Value
public class TwitchRefreshedToken {

    @JsonAlias("access_token") @NonNull String accessToken;
    @JsonAlias("refresh_token") @NonNull String refreshToken;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scope") String[] scopes;

    public @NonNull Token update(@NonNull Token expiredToken) {
        return expiredToken.toBuilder()
                           .accessToken(Secret.with(this.accessToken))
                           .refreshToken(Secret.with(this.refreshToken))
                           .build();
    }

    public @NonNull RefreshedToken toRefreshedToken() {
        return new RefreshedToken(Secret.with(accessToken),Secret.with(refreshToken));
    }

}
