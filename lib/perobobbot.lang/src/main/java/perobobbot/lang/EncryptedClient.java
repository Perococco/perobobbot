package perobobbot.lang;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EncryptedClient extends BaseClient<String> {

    public @NonNull DecryptedClient decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedClient.builder()
                .id(getId())
                .platform(getPlatform())
                .clientId(getClientId())
                .clientSecret(textEncryptor.decrypt(getClientSecret()))
                .build();
    }


}
