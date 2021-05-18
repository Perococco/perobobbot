package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownSafe;
import perobobbot.data.domain.SafeEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SafeRepository extends JpaRepository<SafeEntity, Long> {

    @NonNull Optional<SafeEntity> findByChannelNameAndViewerIdentity_Uuid(@NonNull String channelName,@NonNull UUID viewerIdentityId);

    @NonNull Optional<SafeEntity> findByUuid(@NonNull UUID id);

    default @NonNull SafeEntity getByUuid(@NonNull UUID id) {
        return findByUuid(id).orElseThrow(() -> new UnknownSafe(id));
    }


}
