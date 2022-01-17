package perobobbot.lang.client;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.TextEncryptor;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class EncryptedTwitchClient extends BaseClient<String> implements EncryptedClient {

    @Builder
    public EncryptedTwitchClient(@NonNull UUID id, @NonNull Platform platform, @NonNull String clientId, @NonNull String clientSecret) {
        super(id, platform, clientId, clientSecret);
    }

    public @NonNull DecryptedTwitchClient decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedTwitchClient.builder()
                .id(getId())
                .platform(getPlatform())
                .clientId(getClientId())
                .clientSecret(textEncryptor.decrypt(getClientSecret()))
                .build();
    }


}
