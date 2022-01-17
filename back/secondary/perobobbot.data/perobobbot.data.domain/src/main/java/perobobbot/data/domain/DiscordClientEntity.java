package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.PlatfomId;
import perobobbot.lang.Platform;
import perobobbot.lang.client.EncryptedClient;
import perobobbot.lang.client.EncryptedDiscordClient;
import perobobbot.lang.client.EncryptedTwitchClient;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue(PlatfomId.DISCORD)
public class DiscordClientEntity extends ClientEntity {

    @Column(name = "BOT_TOKEN")
    private String botToken = null;

    public DiscordClientEntity(String clientId, String clientSecret) {
        super(Platform.DISCORD, clientId, clientSecret);
    }

    public @NonNull Optional<String> getBotToken() {
        return Optional.ofNullable(botToken);
    }

    public void setBotToken(@NonNull String botToken) {
        this.botToken = botToken;
    }

    public void clearBotToken() {
        this.botToken = null;
    }

    @Override
    public @NonNull EncryptedDiscordClient toView() {
        return EncryptedDiscordClient.builder()
                                     .id(getUuid())
                                     .platform(getPlatform())
                                     .clientId(getClientId())
                                     .clientSecret(this.getClientSecret())
                                     .botToken(botToken)
                                     .build();

    }
}
