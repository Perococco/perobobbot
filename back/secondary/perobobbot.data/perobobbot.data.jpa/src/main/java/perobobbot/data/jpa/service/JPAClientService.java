package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.domain.base.ClientEntityBase;
import perobobbot.data.jpa.repository.ClientRepository;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.*;

import java.util.Optional;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAClientService implements ClientService {

    private final @NonNull ClientRepository clientRepository;
    private final @NonNull TextEncryptor textEncryptor;

    @Override
    public @NonNull Optional<Client> findClientForPlatform(@NonNull Platform platform) {
        return clientRepository.findFirstByPlatform(platform).map(ClientEntityBase::toView);
    }

    @Override
    public @NonNull Optional<Client> findClient(@NonNull Platform platform, @NonNull String clientId) {
        return clientRepository.findByPlatformAndClientId(platform,clientId).map(ClientEntityBase::toView);
    }

    @Override
    public @NonNull ImmutableList<Client> findAllClients() {
        return clientRepository.findAll()
                               .stream()
                               .map(ClientEntityBase::toView)
                               .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull Client createClient(@NonNull CreateClientParameter parameter) {
        return Todo.TODO();
    }
}
