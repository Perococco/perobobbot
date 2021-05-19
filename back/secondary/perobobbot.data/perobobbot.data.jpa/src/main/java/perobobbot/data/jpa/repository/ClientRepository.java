package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.data.com.NoClientTokenForPlatform;
import perobobbot.data.com.UnknownClient;
import perobobbot.data.com.UnknownClientId;
import perobobbot.data.domain.ClientEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {

    @NonNull Optional<ClientEntity> findFirstByPlatform(@NonNull Platform platform);

    @NonNull Optional<ClientEntity> findByPlatformAndClientId(@NonNull Platform platform, @NonNull String clientId);

    @NonNull Optional<ClientEntity> findClientByUuid(@NonNull UUID clientId);


    default @NonNull ClientEntity getFirstByPlatform(@NonNull Platform platform) {
        return findFirstByPlatform(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }

    default @NonNull ClientEntity getClientByUuid(@NonNull UUID clientId) {
        return findClientByUuid(clientId).orElseThrow(() -> new UnknownClientId(clientId));
    }
    default @NonNull ClientEntity getByPlatformAndClientId(@NonNull Platform platform, @NonNull String clientId) {
        return findByPlatformAndClientId(platform, clientId).orElseThrow(() -> new UnknownClient(platform,clientId));
    }
}
