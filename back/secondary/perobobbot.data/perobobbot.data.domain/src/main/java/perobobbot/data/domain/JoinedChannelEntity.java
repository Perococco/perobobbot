package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import perobobbot.data.com.JoinedChannel;
import perobobbot.data.domain.base.JoinedChannelEntityBase;
import perobobbot.lang.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JOINED_CHANNEL")
@NoArgsConstructor
public class JoinedChannelEntity extends JoinedChannelEntityBase {

    public JoinedChannelEntity(BotEntity bot, Platform platform, String channelName) {
        super(bot, platform, channelName);
    }

    public JoinedChannel toView() {
        return new JoinedChannel(getBot().toView(),getPlatform(),getChannelName());
    }
}
