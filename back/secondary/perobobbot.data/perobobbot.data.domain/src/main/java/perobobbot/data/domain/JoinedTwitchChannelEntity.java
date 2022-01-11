package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.lang.JoinedTwitchChannel;
import perobobbot.lang.TextEncryptor;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "JOINED_TWITCH_CHANNEL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "BOT_ID",
                "TWITCH_USER_ID",
                "CHANNEL_NAME",
        })
})
@NoArgsConstructor
public class JoinedTwitchChannelEntity extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "TWITCH_USER_ID", nullable = false)
    private TwitchUserEntity twitchUser;

    @Column(name = "CHANNEL_NAME",nullable = false)
    private String channelName;

    public JoinedTwitchChannelEntity(
            @NonNull BotEntity bot,
            @NonNull TwitchUserEntity twitchUser,
            @NonNull String channelName) {
        super(UUID.randomUUID());
        this.bot = bot;
        this.twitchUser = twitchUser;
        this.channelName = channelName;
    }

    public @NonNull JoinedTwitchChannel toView(@NonNull TextEncryptor textEncryptor) {

        return new JoinedTwitchChannel(
                getUuid(),
                bot.toView(),
                twitchUser.toView(),
                twitchUser.getUserToken()
                               .map(UserTokenEntity::toTokenInfo)
                               .map(t -> t.decrypt(textEncryptor))
                               .orElse(null),
                channelName);
    }

    public void disconnect() {
        bot.disconnectChannel(this);
    }
}
