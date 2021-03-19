package perobobbot.data.domain.base;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.BotEntity;
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

    @Column(name = "PLATFORM",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform platform;

    @Column(name = "CHANNEL_NAME",nullable = false)
    private String channelName;

    public JoinedChannelEntityBase(@NonNull BotEntity bot, @NonNull Platform platform, @NonNull String channelName) {
        super(UUID.randomUUID());
        this.bot = bot;
        this.platform =platform;
        this.channelName = channelName;
    }
}
