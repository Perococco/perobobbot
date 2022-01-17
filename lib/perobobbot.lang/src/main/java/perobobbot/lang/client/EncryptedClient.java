package perobobbot.lang.client;

import lombok.NonNull;
import perobobbot.lang.TextEncryptor;

public interface EncryptedClient extends Client<String> {

    @NonNull DecryptedClient decrypt(@NonNull TextEncryptor textEncryptor);

}
