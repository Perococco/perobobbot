package perobobbot.data.domain.base;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.lang.Platform;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class ViewerIdentityEntityBase extends PersistentObjectWithUUID {

    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    @Column(name = "PLATFORM", nullable = false)
    private Platform platform;

    /**
     * The id of the viewer of the platform
     */
    @Column(name = "VIEWER_ID", nullable = false)
    private String viewerId = "";

    @Column(name = "LOGIN", nullable = false)
    private String login = "";

    @Column(name = "PSEUDO", nullable = false)
    private String pseudo = "";

    @Getter(AccessLevel.NONE)
    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinColumn(name = "USER_TOKEN_ID",nullable = true)
    private UserTokenEntity userTokenEntity;

    public ViewerIdentityEntityBase(@NonNull Platform platform, @NonNull String viewerId, @NonNull String login) {
        this(platform,viewerId,login,login);
    }

    public ViewerIdentityEntityBase(@NonNull Platform platform, @NonNull String viewerId, @NonNull String login, @NonNull String pseudo) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.viewerId = viewerId;
        this.login = login;
        this.pseudo = pseudo;
    }

    public @NonNull Optional<UserTokenEntity> getUserTokenEntity() {
        return Optional.ofNullable(userTokenEntity);
    }
}
