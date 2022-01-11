package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownPlatformUser;
import perobobbot.data.com.UnknownPlatformUserId;
import perobobbot.data.domain.PlatformUserEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface PlatformUserRepository extends JpaRepository<PlatformUserEntity<?>,Long> {

    @NonNull Optional<PlatformUserEntity<?>> findByPlatformAndUserId(@NonNull Platform platform, @NonNull String userId);


    @NonNull Optional<PlatformUserEntity<?>> findByUuid(@NonNull UUID uuid);

    default @NonNull PlatformUserEntity<?> getByPlatformAndUserId(@NonNull Platform platform, @NonNull String viewerId) {
        return findByPlatformAndUserId(platform, viewerId).orElseThrow(() -> new UnknownPlatformUser(platform, viewerId));
    }

    default @NonNull PlatformUserEntity<?> getByUuid(@NonNull UUID platformUserId) {
        return findByUuid(platformUserId).orElseThrow(() -> new UnknownPlatformUserId(platformUserId));
    }



}
