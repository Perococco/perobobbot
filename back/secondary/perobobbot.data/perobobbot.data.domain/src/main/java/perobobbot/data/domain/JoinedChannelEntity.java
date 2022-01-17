package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.lang.JoinedChannel;
import perobobbot.lang.TextEncryptor;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "JOINED_CHANNEL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "PLATFORM_BOT_ID",
                "CHANNEL_ID",
        })
})
@NoArgsConstructor
public class JoinedChannelEntity extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "PLATFORM_BOT_ID",nullable = false)
    private PlatformBotEntity platformBot;

    @Column(name = "CHANNEL_ID",nullable = false)
    private String channelId;

    public JoinedChannelEntity(
            @NonNull PlatformBotEntity platformBot,
            @NonNull String channelId) {
        super(UUID.randomUUID());
        this.platformBot = platformBot;
        this.channelId = channelId;
    }

    public @NonNull JoinedChannel toView(@NonNull TextEncryptor textEncryptor) {
        final var twitchUser = this.platformBot.getPlatformUser();
        return new JoinedChannel(
                getUuid(),
                this.platformBot.toView(),
                twitchUser.getUserToken()
                               .map(UserTokenEntity::toTokenInfo)
                               .map(t -> t.decrypt(textEncryptor))
                               .orElse(null),
                channelId);
    }

    public void disconnect() {
        platformBot.disconnectChannel(this);
    }
}
