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
public class DecryptedDiscordClient extends BaseClient<Secret> implements DecryptedClient {

    Secret botToken;

    @Builder
    public DecryptedDiscordClient(@NonNull UUID id,
                                  @NonNull Platform platform,
                                  @NonNull String clientId,
                                  @NonNull Secret clientSecret,
                                  Secret botToken) {
        super(id, platform, clientId, clientSecret);
        this.botToken = botToken;
    }

    @Override
    public @NonNull EncryptedDiscordClient encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedDiscordClient.builder()
                                     .id(getId())
                                     .platform(getPlatform())
                                     .clientId(getClientId())
                                     .clientSecret(textEncryptor.encrypt(getClientSecret()))
                                     .botToken(encryptBotToken(textEncryptor))
                                     .build();
    }

    private String encryptBotToken(@NonNull TextEncryptor textEncryptor) {
        return botToken == null ? null : textEncryptor.encrypt(botToken);
    }


}
