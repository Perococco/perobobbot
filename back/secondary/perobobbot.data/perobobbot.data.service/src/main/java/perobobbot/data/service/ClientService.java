package perobobbot.data.service;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.lang.Secret;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.client.DecryptedDiscordClient;
import perobobbot.lang.client.SafeClient;

import java.util.Optional;
import java.util.UUID;


public interface ClientService {

    /**
     * @param platform a platform
     * @return an optional containing the client for the provided platform if one exists,
     * an empty optional otherwise
     */
    @NonNull Optional<DecryptedClient> findClientForPlatform(@NonNull Platform platform);

    /**
     * @param platform the searched platform
     * @return the client for the provided platform
     * @throws NoClientForPlatform if no client has been defined for the provided platform
     */
    default @NonNull DecryptedClient getClient(@NonNull Platform platform) {
        return findClientForPlatform(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }

    /**
     * @param platform the searched platform
     * @return the client for the provided platform stripped from any sensitive information
     */
    default @NonNull SafeClient getSafeClient(@NonNull Platform platform) {
        return getClient(platform).stripSecret();
    }

    /**
     * @return all the clients per platform
     */
    @NonNull ImmutableMap<Platform, DecryptedClient> findAllClients();

    @NonNull DecryptedClient createClient(@NonNull CreateClientParameter parameter);

    @NonNull DecryptedDiscordClient setDiscordBotToken(@NonNull Secret botToken);

    default boolean hasClientForPlatform(Platform platform) {
        return findClientForPlatform(platform).isPresent();
    }
}
