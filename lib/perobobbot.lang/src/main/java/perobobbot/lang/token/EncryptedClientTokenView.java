package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.SafeClient;
import perobobbot.lang.TextEncryptor;

import java.util.UUID;

@Value
public class EncryptedClientTokenView {

    @NonNull UUID id;
    @NonNull SafeClient client;
    @NonNull EncryptedClientToken clientToken;

    public @NonNull DecryptedClientTokenView decrypt(@NonNull TextEncryptor textEncryptor) {
        return new DecryptedClientTokenView(id,client,clientToken.decrypt(textEncryptor));
    }
}
