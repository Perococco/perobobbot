package perobobbot.data.domain.base;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.lang.Platform;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
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
    private String viewerId;

    @Column(name = "PSEUDO", nullable = false)
    private String pseudo;

    public ViewerIdentityEntityBase(@NonNull Platform platform, @NonNull String viewerId, @NonNull String pseudo) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.viewerId = viewerId;
        this.pseudo = pseudo;
    }
}
