package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.com.PointType;
import perobobbot.lang.Platform;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class PointEntityBase extends PersistentObjectWithUUID {


    @Column(name = "PLATFORM", nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull Platform platform;

    @Column(name= "CHANNEL_NAME", nullable = false)
    private @NonNull String channelName;

    @Column(name= "USER_CHAT_ID",nullable = false)
    private @NonNull String userChatId;

    @Column(name ="POINT_TYPE", nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull PointType type;

    @Column(name = "AMOUNT")
    private @NonNull long amount;

    public PointEntityBase(@NonNull Platform platform, @NonNull String channelName, @NonNull String userChatId, @NonNull PointType type) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.channelName = channelName;
        this.userChatId = userChatId;
        this.type = type;
        this.amount = 0;
    }
}
