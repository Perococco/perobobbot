package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownSafe;
import perobobbot.data.domain.SafeEntity;
import perobobbot.lang.Platform;
import perobobbot.lang.PointType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SafeRepository extends JpaRepository<SafeEntity, Long> {


    @NonNull Optional<SafeEntity> findByPlatformAndChannelNameAndUserChatIdAndType(
            @NonNull Platform platform,
            @NonNull String channelName,
            @NonNull String userChatId,
            @NonNull PointType type
            );

    @NonNull Optional<SafeEntity> findByUuid(@NonNull UUID id);

    default @NonNull SafeEntity getByUuid(@NonNull UUID id) {
        return findByUuid(id).orElseThrow(() -> new UnknownSafe(id));
    }


}
