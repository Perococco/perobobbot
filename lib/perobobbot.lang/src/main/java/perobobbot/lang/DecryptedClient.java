package perobobbot.lang;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DecryptedClient extends BaseClient<Secret> {

    public @NonNull EncryptedClient encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedClient.builder()
                              .id(getId())
                              .platform(getPlatform())
                              .clientId(getClientId())
                              .clientSecret(textEncryptor.encrypt(getClientSecret()))
                              .build();
    }

}
