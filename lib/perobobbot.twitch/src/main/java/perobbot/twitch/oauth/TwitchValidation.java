package perobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class TwitchValidation {

    @JsonAlias("client_id") @NonNull String clientId;
    @JsonAlias("login")  @NonNull String login;
    @JsonAlias("scopes") @NonNull String[] scopes;
    @JsonAlias("user_id") @NonNull String user_id;
    @JsonAlias("expires_in") double expiresIn;

}
