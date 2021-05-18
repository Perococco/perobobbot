package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.BotHasThisExtensionAlready;
import perobobbot.data.domain.base.BotEntityBase;
import perobobbot.lang.Bot;
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


    public @NonNull BotExtensionEntity addExtension(@NonNull ExtensionEntity extension) {
        if (hasExtension(extension)) {
            throw new BotHasThisExtensionAlready(this.uuid,extension.getUuid());
        }
        final var botExtension = new BotExtensionEntity(this,extension);
        this.getBotExtensions().add(botExtension);
        return botExtension;
    }

    /**
     * @param extensionName the name of the extension to find
     * @return the found extension in an optional if found, en empty optional otherwise
     */
    public @NonNull Optional<BotExtensionEntity> findBotExtension(@NonNull String extensionName) {
        return getBotExtensions().stream()
                                 .filter(e -> e.getExtension().getName().equals(extensionName))
                                 .findAny();
    }

    public boolean hasExtension(@NonNull ExtensionEntity extension) {
        return findBotExtension(extension.getName()).isPresent();
    }

    public @NonNull Bot toView() {
        return Bot.builder()
                  .id(uuid)
                  .ownerLogin(getOwnerLogin())
                  .name(getName())
                  .build();
    }

    public @NonNull JoinedChannelEntity joinChannel(@NonNull ViewerIdentityEntity viewerIdentity, @NonNull String channelName) {
        return new JoinedChannelEntity(this,viewerIdentity,channelName);
    }

    void disconnectChannel(@NonNull JoinedChannelEntity joinedChannel) {
        getJoinedChannels().removeIf(jc -> jc.getUuid().equals(joinedChannel.getUuid()));
    }
}
