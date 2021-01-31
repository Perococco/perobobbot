package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.PointType;
import perobobbot.data.domain.base.PointEntityBase;
import perobobbot.lang.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "POINT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointEntity extends PointEntityBase {

    public PointEntity(@NonNull Platform platform,
                       @NonNull String channelName,
                       @NonNull String userChatId,
                       @NonNull PointType type) {
        super(platform, channelName, userChatId, type);
    }



}
