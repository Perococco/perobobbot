package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownClient;
import perobobbot.data.domain.ClientEntity;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {

    @NonNull Optional<ClientEntity> findFirstByPlatform(@NonNull Platform platform);

    @NonNull Optional<ClientEntity> findClientByUuid(@NonNull UUID clientId);

    default @NonNull ClientEntity getClientByUuid(@NonNull UUID clientId) {
        return findClientByUuid(clientId).orElseThrow(() -> new UnknownClient(clientId));
    }
}
