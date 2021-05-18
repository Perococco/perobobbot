package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TextEncryptor;

import java.util.UUID;

@Value
public class EncryptedClientTokenView {

    @NonNull UUID id;
    @NonNull String clientId;
    @NonNull EncryptedClientToken clientToken;

    public @NonNull DecryptedClientTokenView decrypt(@NonNull TextEncryptor textEncryptor) {
        return new DecryptedClientTokenView(id,clientId,clientToken.decrypt(textEncryptor));
    }
}
