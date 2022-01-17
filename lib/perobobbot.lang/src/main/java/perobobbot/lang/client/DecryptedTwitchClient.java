package perobobbot.lang.client;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class DecryptedTwitchClient extends BaseClient<Secret> implements DecryptedClient {

    @Builder
    public DecryptedTwitchClient(@NonNull UUID id, @NonNull Platform platform, @NonNull String clientId, @NonNull Secret clientSecret) {
        super(id, platform, clientId, clientSecret);
    }

    public @NonNull EncryptedTwitchClient encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedTwitchClient.builder()
                              .id(getId())
                              .platform(getPlatform())
                              .clientId(getClientId())
                              .clientSecret(textEncryptor.encrypt(getClientSecret()))
                              .build();
    }

}
