package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.SafeClient;
import perobobbot.lang.TextEncryptor;

import java.util.UUID;

@Value
public class DecryptedClientTokenView {

    @NonNull UUID id;
    @NonNull SafeClient client;
    @NonNull DecryptedClientToken clientToken;

    public @NonNull EncryptedClientTokenView encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedClientTokenView(id,client,clientToken.encrypt(textEncryptor));
    }
}
