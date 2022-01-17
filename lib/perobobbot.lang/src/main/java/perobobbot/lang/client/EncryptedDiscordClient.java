package perobobbot.lang.client;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

import java.util.Optional;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class EncryptedDiscordClient extends BaseClient<String> implements EncryptedClient {

    String botToken;

    @Builder
    public EncryptedDiscordClient(@NonNull UUID id,
                                  @NonNull Platform platform,
                                  @NonNull String clientId,
                                  @NonNull String clientSecret,
                                  String botToken) {
        super(id, platform, clientId, clientSecret);
        this.botToken = botToken;
    }

    public @NonNull DecryptedDiscordClient decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedDiscordClient.builder()
                .id(getId())
                .platform(getPlatform())
                .clientId(getClientId())
                .clientSecret(textEncryptor.decrypt(getClientSecret()))
                .botToken(decryptBotToken(textEncryptor))
                .build();
    }

    private Secret decryptBotToken(@NonNull TextEncryptor textEncryptor) {
        return botToken == null ? null : textEncryptor.decrypt(botToken);
    }


}
