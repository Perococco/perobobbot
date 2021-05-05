package perobobbot.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class Token {

    @JsonAlias("access_token") @NonNull String accessToken;
    @JsonAlias("refresh_token") @NonNull String refreshToken;
    @JsonAlias("expires_in") double expiresIn;
    @JsonAlias("scope") @NonNull String[] scopes;
    @JsonAlias("token_type") @NonNull String tokenType;

}
