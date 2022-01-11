package perobobbot.data.jpa.test.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

@Component
public class TextEncryptorForTest implements TextEncryptor {

    @Override
    public @NonNull String encrypt(@NonNull Secret secret) {
        return "S_"+secret.getValue();
    }

    @Override
    public @NonNull Secret decrypt(@NonNull String encryptedText) {
        return Secret.with(encryptedText.substring(2));
    }
}
