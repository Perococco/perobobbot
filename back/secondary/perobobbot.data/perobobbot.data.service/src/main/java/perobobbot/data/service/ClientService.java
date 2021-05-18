package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.NoClientForPlatform;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;

import java.util.Optional;

public interface ClientService {

    @NonNull Optional<Client> findClient(@NonNull Platform platform);

    default @NonNull Client getClient(@NonNull Platform platform) {
        return findClient(platform).orElseThrow(() -> new NoClientForPlatform(platform));
    }


}
