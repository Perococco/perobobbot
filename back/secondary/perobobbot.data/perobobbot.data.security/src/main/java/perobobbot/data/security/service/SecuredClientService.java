package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.Platform;

import java.util.Optional;

@Service
@SecuredService
@RequiredArgsConstructor
public class SecuredClientService implements ClientService {

    private final @NonNull @EventService
    ClientService clientService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull Optional<DecryptedClient> findClientForPlatform(@NonNull Platform platform) {
        return clientService.findClientForPlatform(platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull Optional<DecryptedClient> findClient(@NonNull Platform platform, @NonNull String clientId) {
        return clientService.findClient(platform,clientId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<DecryptedClient> findAllClients() {
        return clientService.findAllClients();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull DecryptedClient getClient(@NonNull Platform platform) {
        return clientService.getClient(platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull DecryptedClient getClient(@NonNull Platform platform, @NonNull String clientId) {
        return clientService.getClient(platform, clientId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull DecryptedClient createClient(@NonNull CreateClientParameter parameter) {
        return clientService.createClient(parameter);
    }
}
