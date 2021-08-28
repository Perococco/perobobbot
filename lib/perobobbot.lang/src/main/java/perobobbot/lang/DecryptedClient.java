package perobobbot.lang;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class DecryptedClient extends BaseClient<Secret> {

    @Builder
    public DecryptedClient(@NonNull UUID id, @NonNull Platform platform, @NonNull String clientId, @NonNull Secret clientSecret) {
        super(id, platform, clientId, clientSecret);
    }

    public @NonNull EncryptedClient encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedClient.builder()
                              .id(getId())
                              .platform(getPlatform())
                              .clientId(getClientId())
                              .clientSecret(textEncryptor.encrypt(getClientSecret()))
                              .build();
    }

}
