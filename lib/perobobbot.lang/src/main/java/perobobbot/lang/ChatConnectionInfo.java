package perobobbot.lang;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.UUID;

@Value
@EqualsAndHashCode(of = {"botId","viewerIdentityId"})
public class ChatConnectionInfo {

    @NonNull UUID botId;

    @NonNull DecryptedUserTokenView userToken;
    
    /**
     * for logging purpose
     */
    @NonNull String botName;

    public @NonNull Platform getPlatform() {
        return userToken.getPlatform();
    }

    public @NonNull ViewerIdentity getViewerIdentity() {
        return userToken.getViewerIdentity();
    }

    public @NonNull String getViewerPseudo() {
        return userToken.getViewerPseudo();
    }
}
