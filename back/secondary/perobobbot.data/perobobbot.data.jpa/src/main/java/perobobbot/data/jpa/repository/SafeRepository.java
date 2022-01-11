package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownSafe;
import perobobbot.data.domain.SafeEntity;

import java.util.Optional;
import java.util.UUID;

public interface SafeRepository extends JpaRepository<SafeEntity, Long> {

    @NonNull Optional<SafeEntity> findByChannelIdAndPlatformUser_Uuid(@NonNull String channelName,@NonNull UUID userIdentiticationId);

    @NonNull Optional<SafeEntity> findByUuid(@NonNull UUID id);

    default @NonNull SafeEntity getByUuid(@NonNull UUID id) {
        return findByUuid(id).orElseThrow(() -> new UnknownSafe(id));
    }


}
