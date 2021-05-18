package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Platform;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class JoinedChannelEntityBase extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "VIEWER_IDENTITY_ID", nullable = false)
    private ViewerIdentityEntity viewerIdentity;

    @Column(name = "CHANNEL_NAME",nullable = false)
    private String channelName;

    public JoinedChannelEntityBase(
            @NonNull BotEntity bot,
            @NonNull ViewerIdentityEntity viewerIdentity,
            @NonNull String channelName) {
        super(UUID.randomUUID());
        this.bot = bot;
        this.viewerIdentity = viewerIdentity;
        this.channelName = channelName;
    }
}
