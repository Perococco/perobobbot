package perobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import perobobbot.oauth.UserIdentity;

import java.util.Optional;

@Value
@ToString
public class TwitchValidation {

    @JsonAlias("client_id") @NonNull String clientId;
    @Getter(AccessLevel.NONE)
    @JsonAlias("login")  String login;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scopes") String[] scopes;
    @Getter(AccessLevel.NONE)
    @JsonAlias("user_id") String userId;
    @JsonAlias("expires_in") int expiresIn;

    public @NonNull Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }

    public @NonNull Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }

    public @NonNull String[] getScopes() {
        return scopes == null ? new String[0]:scopes;
    }

    public @NonNull UserIdentity toUserIdentity() {
        return new UserIdentity(userId,login);
    }
}
