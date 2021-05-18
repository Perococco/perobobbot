package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.JoinedChannel;
import perobobbot.data.domain.base.JoinedChannelEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JOINED_CHANNEL")
@NoArgsConstructor
public class JoinedChannelEntity extends JoinedChannelEntityBase {

    public JoinedChannelEntity(@NonNull BotEntity bot,
                               @NonNull ViewerIdentityEntity viewerIdentity,
                               @NonNull String channelName) {
        super(bot, viewerIdentity, channelName);
    }

    public @NonNull JoinedChannel toView() {
        return new JoinedChannel(getUuid(), getBot().toView(),
                                 getViewerIdentity().toView(),
                                 getChannelName());
    }

    public void disconnect() {
        getBot().disconnectChannel(this);
    }
}
