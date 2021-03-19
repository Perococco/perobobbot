package perobobbot.data.domain;

import com.google.common.collect.ImmutableMap;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.BotHasThisExtensionAlready;
import perobobbot.data.domain.base.BotEntityBase;
import perobobbot.lang.Bot;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

@Entity
@Table(name = "BOT")
@NoArgsConstructor
public class BotEntity extends BotEntityBase {

    public BotEntity(UserEntity owner, @NonNull String name) {
        super(owner,name);
    }

    public @NonNull String getOwnerLogin() {
        return getOwner().getLogin();
    }

    public @NonNull ImmutableMap<Platform, Credential> getCredentialsAsMap() {
        return credentials()
                .collect(ImmutableMap.toImmutableMap(TokenEntity::getPlatform, TokenEntity::getCredential));
    }

    public void attachCredential(@NonNull TokenEntity credentialEntity) {
        if (!credentialEntity.getOwner().equals(this.getOwner())) {
            throw new IllegalArgumentException("Invalid credential. Does not belong to the bot owner");
        }
        if (isAlreadyAttachedToThisCredential(credentialEntity)) {
            throw new IllegalArgumentException("Credential already used");
        }
        this.getBotCredentials().add(new BotCredentialEntity(this,credentialEntity));
     }

     public boolean isAlreadyAttachedToThisCredential(@NonNull TokenEntity credentialEntity) {
         return getBotCredentials().stream().anyMatch(e -> e.getCredentialEntity().equals(credentialEntity));
     }

    public @NonNull BotExtensionEntity addExtension(@NonNull ExtensionEntity extension) {
        if (hasExtension(extension)) {
            throw new BotHasThisExtensionAlready(this.uuid,extension.getUuid());
        }
        final var botExtension = new BotExtensionEntity(this,extension);
        this.getBotExtensions().add(botExtension);
        return botExtension;
    }

    public @NonNull Optional<BotExtensionEntity> findBotExtension(@NonNull String extensionName) {
        return botExtensions().filter(e -> e.getExtension().getName().equals(extensionName)).findAny();
    }

    public boolean hasExtension(@NonNull ExtensionEntity extension) {
        return findBotExtension(extension.getName()).isPresent();
    }

    public @NonNull Bot toView() {
        return Bot.builder()
                  .id(uuid)
                  .ownerLogin(getOwnerLogin())
                  .name(getName())
                  .credentials(getCredentialsAsMap())
                  .build();
    }

    public @NonNull JoinedChannelEntity createJoinedChannel(@NonNull Platform platform, @NonNull String channelName) {
        return new JoinedChannelEntity(this,platform,channelName);
    }
}
