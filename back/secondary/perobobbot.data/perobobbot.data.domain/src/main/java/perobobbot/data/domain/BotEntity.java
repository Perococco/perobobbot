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
                .collect(ImmutableMap.toImmutableMap(CredentialEntity::getPlatform, CredentialEntity::getCredential));
    }

    public @NonNull BotExtensionEntity addExtension(@NonNull ExtensionEntity extension) {
        if (extensions().anyMatch(e -> e.equals(extension))) {
            throw new BotHasThisExtensionAlready(this.uuid,extension.getUuid());
        }
        final var botExtension = new BotExtensionEntity(this,extension);
        this.getBotExtensions().add(botExtension);
        return botExtension;
    }

    public @NonNull Bot toView() {
        return Bot.builder()
                  .id(uuid)
                  .ownerLogin(getOwnerLogin())
                  .name(getName())
                  .credentials(getCredentialsAsMap())
                  .build();
    }
}
