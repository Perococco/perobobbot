package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownViewIdentity;
import perobobbot.data.com.UnknownViewIdentityId;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface ViewerIdentityRepository extends JpaRepository<ViewerIdentityEntity,Long> {

    @NonNull Optional<ViewerIdentityEntity> findByPlatformAndViewerId(@NonNull Platform platform, @NonNull String viewerId);

    @NonNull Optional<ViewerIdentityEntity> findByUuid(@NonNull UUID viewIdentityId);


    default @NonNull ViewerIdentityEntity getByPlatformAndViewerId(@NonNull Platform platform, @NonNull String viewerId) {
        return findByPlatformAndViewerId(platform,viewerId).orElseThrow(() -> new UnknownViewIdentity(platform,viewerId));
    }

    default @NonNull ViewerIdentityEntity getByUuid(@NonNull UUID viewIdentityId) {
        return findByUuid(viewIdentityId).orElseThrow(() -> new UnknownViewIdentityId(viewIdentityId));
    }

}
