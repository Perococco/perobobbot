package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.data.com.UnknownClient;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.Platform;

import java.util.Optional;

public interface ClientService {

    @NonNull Optional<DecryptedClient> findClientForPlatform(@NonNull Platform platform);

    @NonNull Optional<DecryptedClient> findClient(@NonNull Platform platform, @NonNull String clientId);

    default @NonNull DecryptedClient getClient(@NonNull Platform platform) {
        return findClientForPlatform(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }

    default @NonNull DecryptedClient getClient(@NonNull Platform platform, @NonNull String clientId) {
        return findClient(platform,clientId).orElseThrow(() -> new UnknownClient(platform,clientId));
    }

    @NonNull ImmutableList<DecryptedClient> findAllClients();

    @NonNull DecryptedClient createClient(@NonNull CreateClientParameter parameter);
}
