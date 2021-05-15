package perobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class TwitchValidation {

    @JsonAlias("client_id") @NonNull String clientId;
    @Getter(AccessLevel.NONE)
    @JsonAlias("login")  String login;
    @JsonAlias("scopes") @NonNull String[] scopes;
    @Getter(AccessLevel.NONE)
    @JsonAlias("user_id") String userId;
    @JsonAlias("expires_in") double expiresIn;

    public @NonNull Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }

    public @NonNull Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }
}
