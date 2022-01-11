package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.data.com.UnknownClient;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.SafeClient;

import java.util.Optional;

//TODO : there can be only 1 client per platform. This API and the implementation does
//not use this information.

public interface ClientService {

    /**
     * @param platform a platform
     * @return an optional containing the client for the provided platform if one exists,
     * an empty optional otherwise
     */
    @NonNull Optional<DecryptedClient> findClientForPlatform(@NonNull Platform platform);

    default @NonNull DecryptedClient getClient(@NonNull Platform platform) {
        return findClientForPlatform(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }

    default @NonNull SafeClient getSafeClient(@NonNull Platform platform) {
        return getClient(platform).stripSecret();
    }

    @NonNull ImmutableMap<Platform, DecryptedClient> findAllClients();

    @NonNull DecryptedClient createClient(@NonNull CreateClientParameter parameter);

    default boolean hasClientForPlatform(Platform platform) {
        return findClientForPlatform(platform).isPresent();
    }
}
