package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.JoinedChannelEntityBase;
import perobobbot.lang.JoinedChannel;
import perobobbot.lang.TextEncryptor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "JOINED_CHANNEL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                JoinedChannelEntityBase.BOT_ID_COLUMN_NAME,
                JoinedChannelEntityBase.VIEWER_IDENTITY_ID_COLUMN_NAME,
                JoinedChannelEntityBase.CHANNEL_NAME_COLUMN_NAME,
        })
})
@NoArgsConstructor
public class JoinedChannelEntity extends JoinedChannelEntityBase {

    public JoinedChannelEntity(@NonNull BotEntity bot,
                               @NonNull ViewerIdentityEntity viewerIdentity,
                               @NonNull String channelName) {
        super(bot, viewerIdentity, channelName);
    }

    public @NonNull JoinedChannel toView(@NonNull TextEncryptor textEncryptor) {

        return new JoinedChannel(
                getUuid(),
                getBot().toView(),
                getViewerIdentity().toView(),
                getViewerIdentity().getUserTokenEntity()
                                   .map(UserTokenEntity::toTokenInfo)
                                   .map(t -> t.decrypt(textEncryptor))
                                   .orElse(null),
                getChannelName());
    }

    public void disconnect() {
        getBot().disconnectChannel(this);
    }
}
