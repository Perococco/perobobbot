package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.ViewerIdentity;

import java.time.Instant;
import java.util.UUID;

@Value
public class DecryptedUserTokenView {

    @NonNull UUID id;
    @NonNull String ownerLogin;
    @NonNull ViewerIdentity viewerIdentity;
    @NonNull DecryptedUserToken userToken;

    public @NonNull Platform getPlatform() {
        return viewerIdentity.getPlatform();
    }

    public String getViewerPseudo() {
        return viewerIdentity.getPseudo();
    }

    public Instant getExpirationInstant() {
        return userToken.getExpirationInstant();
    }
}
