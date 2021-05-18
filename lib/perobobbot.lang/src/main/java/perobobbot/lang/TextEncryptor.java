package perobobbot.lang;

import lombok.NonNull;

public interface TextEncryptor {

    @NonNull String encrypt(@NonNull Secret secret);

    /**
     * Decrypt the encrypted text string.
     */
    @NonNull Secret decrypt(@NonNull String encryptedText);

}
