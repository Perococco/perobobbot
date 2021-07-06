package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
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

    public static final String BOT_ID_COLUMN_NAME = "BOT_ID";
    public static final String VIEWER_IDENTITY_ID_COLUMN_NAME = "VIEWER_IDENTITY_ID";
    public static final String CHANNEL_NAME_COLUMN_NAME = "CHANNEL_NAME";

    @ManyToOne
    @JoinColumn(name = BOT_ID_COLUMN_NAME,nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = VIEWER_IDENTITY_ID_COLUMN_NAME, nullable = false)
    private ViewerIdentityEntity viewerIdentity;

    @Column(name = CHANNEL_NAME_COLUMN_NAME,nullable = false)
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
