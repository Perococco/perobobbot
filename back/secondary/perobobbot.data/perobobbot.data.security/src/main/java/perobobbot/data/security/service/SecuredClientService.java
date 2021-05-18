package perobobbot.data.security.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.Client;
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
    public @NonNull Optional<Client> findClient(@NonNull Platform platform) {
        return clientService.findClient(platform);
    }
}
