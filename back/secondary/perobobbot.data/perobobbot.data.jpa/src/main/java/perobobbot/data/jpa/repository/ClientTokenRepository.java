package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownClientId;
import perobobbot.data.domain.ClientTokenEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface ClientTokenRepository extends JpaRepository<ClientTokenEntity,Long> {

    @NonNull Optional<ClientTokenEntity> findByUuid(@NonNull UUID id);

    default @NonNull ClientTokenEntity getByUuid(@NonNull UUID id) {
        return findByUuid(id).orElseThrow(() -> new UnknownClientId(id));
    }

    @NonNull Optional<ClientTokenEntity> findByClient_Platform(@NonNull Platform platform);

    int removeByClient_UuidAndAccessToken(@NonNull UUID clientId, @NonNull String accessToken);

}
