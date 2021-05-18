package perobobbot.server.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

@Component
public class PerobobbotTextEncryptor implements TextEncryptor {

    private final @NonNull org.springframework.security.crypto.encrypt.TextEncryptor delegate;


    public PerobobbotTextEncryptor(
            @Value("${database.secret}") String databaseSecret,
            @Value("${database.salt}") String databaseSalt) {
        delegate = Encryptors.delux(databaseSecret,databaseSalt);
    }

    @Override
    public @NonNull String encrypt(@NonNull Secret secret) {
        return delegate.encrypt(secret.getValue());
    }

    @Override
    public @NonNull Secret decrypt(@NonNull String encryptedText) {
        return Secret.with(delegate.decrypt(encryptedText));
    }
}
