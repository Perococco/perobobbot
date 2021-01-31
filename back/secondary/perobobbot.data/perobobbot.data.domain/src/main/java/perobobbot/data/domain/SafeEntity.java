package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.PointType;
import perobobbot.data.domain.base.SafeEntityBase;
import perobobbot.lang.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SAFE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SafeEntity extends SafeEntityBase {

    public SafeEntity(@NonNull Platform platform,
                      @NonNull String channelName,
                      @NonNull String userChatId,
                      @NonNull PointType type) {
        super(platform, channelName, userChatId, type);
    }




}
