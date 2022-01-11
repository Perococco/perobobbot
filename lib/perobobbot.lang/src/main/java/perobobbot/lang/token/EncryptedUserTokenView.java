package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformUser;
import perobobbot.lang.TextEncryptor;

import java.util.UUID;

@Value
public class EncryptedUserTokenView  implements TokenView<String> {

    @NonNull UUID id;
    @NonNull String ownerLogin;
    boolean main;
    @NonNull PlatformUser platformUser;
    @NonNull EncryptedUserToken userToken;

    public @NonNull Platform getPlatform() {
        return platformUser.getPlatform();
    }
    public String getUserPseudo() {
        return platformUser.getPseudo();
    }

    public @NonNull DecryptedUserTokenView decrypt(@NonNull TextEncryptor textEncryptor) {
        return new DecryptedUserTokenView(id,ownerLogin,main, platformUser,userToken.decrypt(textEncryptor));
    }

    @Override
    public @NonNull Token<String> getToken() {
        return userToken;
    }
}
