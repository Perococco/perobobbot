package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.data.com.UnknownClientId;
import perobobbot.data.domain.ClientEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {

    @NonNull Optional<ClientEntity> findByPlatform(@NonNull Platform platform);

    @NonNull Optional<ClientEntity> findClientByUuid(@NonNull UUID clientId);


    default @NonNull ClientEntity getByPlatform(@NonNull Platform platform) {
        return findByPlatform(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }

    default @NonNull ClientEntity getClientByUuid(@NonNull UUID clientId) {
        return findClientByUuid(clientId).orElseThrow(() -> new UnknownClientId(clientId));
    }
}
