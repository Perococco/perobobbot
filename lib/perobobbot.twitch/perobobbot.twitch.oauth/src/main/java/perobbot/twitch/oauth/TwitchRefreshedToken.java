package perobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.oauth.Token;

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
