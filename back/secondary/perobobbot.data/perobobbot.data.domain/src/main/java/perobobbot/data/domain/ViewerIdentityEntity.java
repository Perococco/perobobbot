package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.ViewerIdentityEntityBase;
import perobobbot.lang.Platform;
import perobobbot.lang.PointType;
import perobobbot.lang.ViewerIdentity;
import perobobbot.lang.token.EncryptedUserToken;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VIEWER_IDENTITY")
@NoArgsConstructor
public class ViewerIdentityEntity extends ViewerIdentityEntityBase {

    public ViewerIdentityEntity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String login) {
        super(platform, viewerId, login);
    }

    public ViewerIdentityEntity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String login, @NonNull String pseudo) {
        super(platform, viewerId, login, pseudo);
    }

    public @NonNull ViewerIdentity toView() {
        return ViewerIdentity.builder()
                             .id(getUuid())
                             .platform(this.getPlatform())
                             .viewerId(this.getViewerId())
                             .login(this.getLogin())
                             .pseudo(this.getPseudo())
                             .build();
    }
    
    public @NonNull SafeEntity createSafe(@NonNull String channelName) {
        return new SafeEntity(this, channelName, PointType.CREDIT);
    }

    @NonNull UserTokenEntity setUserToken(@NonNull UserEntity owner, @NonNull EncryptedUserToken token) {
        final var result = new UserTokenEntity(owner,this,token);
        this.setUserTokenEntity(result);
        return result;
    }
}
