package perobobbot.lang.client;

import lombok.NonNull;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

public interface DecryptedClient extends Client<Secret> {

    @NonNull EncryptedClient encrypt(@NonNull TextEncryptor textEncryptor);
}
