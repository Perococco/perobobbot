package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownPlatformBot;
import perobobbot.data.domain.PlatformBotEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * @author perococco
 */
public interface PlatformBotRepository extends JpaRepository<PlatformBotEntity, Long> {

    @NonNull Optional<PlatformBotEntity> findByUuid(@NonNull UUID uuid);

    default @NonNull PlatformBotEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownPlatformBot(uuid));
    }

}
