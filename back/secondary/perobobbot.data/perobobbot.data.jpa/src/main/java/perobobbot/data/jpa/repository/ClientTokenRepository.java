package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.ClientTokenEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface ClientTokenRepository extends JpaRepository<ClientTokenEntity,Long> {

    @NonNull Optional<ClientTokenEntity> findByUuid(@NonNull UUID uuid);

    @NonNull Optional<ClientTokenEntity> findByPlatformAndClient_ClientId(@NonNull Platform platform, @NonNull String clientId);

    int removeByClient_UuidAndAccessToken(@NonNull UUID clientId, @NonNull String accessToken);

}
