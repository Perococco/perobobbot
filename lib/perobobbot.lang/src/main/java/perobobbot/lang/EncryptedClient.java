package perobobbot.lang;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class EncryptedClient extends BaseClient<String> {

    @Builder
    public EncryptedClient(@NonNull UUID id, @NonNull Platform platform, @NonNull String clientId, @NonNull String clientSecret) {
        super(id, platform, clientId, clientSecret);
    }

    public @NonNull DecryptedClient decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedClient.builder()
                .id(getId())
                .platform(getPlatform())
                .clientId(getClientId())
                .clientSecret(textEncryptor.decrypt(getClientSecret()))
                .build();
    }


}
