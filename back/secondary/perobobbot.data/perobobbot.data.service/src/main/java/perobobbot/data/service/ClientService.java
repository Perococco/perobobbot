package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.data.com.UnknownClient;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;

import java.util.Optional;

public interface ClientService {

    @NonNull Optional<Client> findClientForPlatform(@NonNull Platform platform);

    @NonNull Optional<Client> findClient(@NonNull Platform platform, @NonNull String clientId);

    default @NonNull Client getClient(@NonNull Platform platform) {
        return findClientForPlatform(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }

    default @NonNull Client getClient(@NonNull Platform platform, @NonNull String clientId) {
        return findClient(platform,clientId).orElseThrow(() -> new UnknownClient(platform,clientId));
    }

}
