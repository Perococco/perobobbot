package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.com.UnknownViewIdentity;
import perobobbot.data.com.UnknownViewIdentityId;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ViewerIdentityRepository extends JpaRepository<ViewerIdentityEntity, Long> {

    @NonNull Optional<ViewerIdentityEntity> findByPlatformAndViewerId(@NonNull Platform platform, @NonNull String viewerId);

    @NonNull Optional<ViewerIdentityEntity> findByUuid(@NonNull UUID viewIdentityId);

    @Query("""
            select v from ViewerIdentityEntity as v
            where v.platform = :platform and (v.viewerId = :viewerInfo or UPPER(v.pseudo) = UPPER(:viewerInfo) or v.login = :viewerInfo) 
            """)
    @NonNull List<ViewerIdentityEntity> findFromViewerInfo(@NonNull Platform platform, @NonNull String viewerInfo);


    default @NonNull ViewerIdentityEntity getByPlatformAndViewerId(@NonNull Platform platform, @NonNull String viewerId) {
        return findByPlatformAndViewerId(platform, viewerId).orElseThrow(() -> new UnknownViewIdentity(platform, viewerId));
    }

    default @NonNull ViewerIdentityEntity getByUuid(@NonNull UUID viewIdentityId) {
        return findByUuid(viewIdentityId).orElseThrow(() -> new UnknownViewIdentityId(viewIdentityId));
    }

}
