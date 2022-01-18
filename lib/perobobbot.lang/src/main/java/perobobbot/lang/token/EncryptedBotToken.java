package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TextEncryptor;

@Value
public class EncryptedBotToken {

    @NonNull String clientId;
    @NonNull String botToken;


    public @NonNull DecryptedBotToken decrypt(@NonNull TextEncryptor textEncryptor) {
        return new DecryptedBotToken(clientId, textEncryptor.decrypt(botToken));
    }
}
