package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.ViewerIdentity;

import java.time.Instant;
import java.util.UUID;

@Value
public class DecryptedUserTokenView  implements DecryptedTokenView {

    @NonNull UUID id;
    @NonNull String ownerLogin;
    boolean main;
    @NonNull ViewerIdentity viewerIdentity;
    @NonNull DecryptedUserToken userToken;

    public @NonNull Platform getPlatform() {
        return viewerIdentity.getPlatform();
    }

    public String getViewerPseudo() {
        return viewerIdentity.getPseudo();
    }
    public String getViewerLogin() {
        return viewerIdentity.getLogin();
    }

    public Instant getExpirationInstant() {
        return userToken.getExpirationInstant();
    }

    @Override
    public @NonNull Token<Secret> getToken() {
        return userToken;
    }

}
