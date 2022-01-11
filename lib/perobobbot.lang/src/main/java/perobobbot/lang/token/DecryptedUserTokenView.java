package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformUser;
import perobobbot.lang.UserIdentification;
import perobobbot.lang.Secret;

import java.time.Instant;
import java.util.UUID;

@Value
public class DecryptedUserTokenView  implements DecryptedTokenView {

    @NonNull UUID id;
    @NonNull String ownerLogin;
    boolean main;
    @NonNull PlatformUser platformUser;
    @NonNull DecryptedUserToken userToken;


    @NonNull
    public Platform getPlatform() {
        return platformUser.getPlatform();
    }

    public String getUserId() {
        return platformUser.getUserId();
    }
    public String getUserPseudo() {
        return platformUser.getPseudo();
    }
    public String getUserLogin() {
        return platformUser.getLogin();
    }

    public Instant getExpirationInstant() {
        return userToken.getExpirationInstant();
    }

    @Override
    public @NonNull Token<Secret> getToken() {
        return userToken;
    }

}
