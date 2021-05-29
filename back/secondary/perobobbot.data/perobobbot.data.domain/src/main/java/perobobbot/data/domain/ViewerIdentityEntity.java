package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.ViewerIdentityEntityBase;
import perobobbot.lang.Platform;
import perobobbot.lang.PointType;
import perobobbot.lang.ViewerIdentity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VIEWER_IDENTITY")
@NoArgsConstructor
public class ViewerIdentityEntity extends ViewerIdentityEntityBase {

    public ViewerIdentityEntity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String pseudo) {
        super(platform, viewerId, pseudo);
    }

    public @NonNull ViewerIdentity toView() {
        return ViewerIdentity.builder()
                             .id(getUuid())
                             .platform(this.getPlatform())
                             .viewerId(this.getViewerId())
                             .pseudo(this.getPseudo())
                             .build();
    }
    
    public @NonNull SafeEntity createSafe(@NonNull String channelName) {
        return new SafeEntity(this, channelName, PointType.CREDIT);
    }
}
