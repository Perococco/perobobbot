package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.TextEncryptor;
import perobobbot.lang.ViewerIdentity;

import java.util.UUID;

@Value
public class EncryptedUserTokenView  implements TokenView<String> {

    @NonNull UUID id;
    @NonNull String ownerLogin;
    boolean main;
    @NonNull ViewerIdentity viewerIdentity;
    @NonNull EncryptedUserToken userToken;

    public @NonNull Platform getPlatform() {
        return viewerIdentity.getPlatform();
    }

    public String getViewerPseudo() {
        return viewerIdentity.getPseudo();
    }

    public @NonNull DecryptedUserTokenView decrypt(@NonNull TextEncryptor textEncryptor) {
        return new DecryptedUserTokenView(id,ownerLogin,main,viewerIdentity,userToken.decrypt(textEncryptor));
    }

    @Override
    public @NonNull Token<String> getToken() {
        return userToken;
    }
}
