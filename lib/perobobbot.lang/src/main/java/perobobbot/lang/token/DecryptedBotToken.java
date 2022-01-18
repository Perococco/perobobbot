package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

@Value
public class DecryptedBotToken {

    @NonNull String clientId;
    @NonNull Secret botToken;


    public @NonNull EncryptedBotToken encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedBotToken(clientId, textEncryptor.encrypt(botToken));
    }
}
