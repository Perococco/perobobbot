package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.domain.ClientEntity;
import perobobbot.data.jpa.repository.ClientRepository;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.client.Client;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.TextEncryptor;

import java.util.Optional;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAClientService implements ClientService {

    private final @NonNull ClientRepository clientRepository;
    private final @NonNull TextEncryptor textEncryptor;

    @Override
    public @NonNull Optional<DecryptedClient> findClientForPlatform(@NonNull Platform platform) {
        return clientRepository.findByPlatform(platform)
                               .map(ClientEntity::toView)
                               .map(t -> t.decrypt(textEncryptor));
    }


    @Override
    public @NonNull ImmutableMap<Platform, DecryptedClient> findAllClients() {
        return clientRepository.findAll()
                               .stream()
                               .map(ClientEntity::toView)
                               .map(t -> t.decrypt(textEncryptor))
                               .collect(ImmutableMap.toImmutableMap(Client::getPlatform, c -> c));
    }

    @Override
    @Transactional
    public @NonNull DecryptedClient createClient(@NonNull CreateClientParameter parameter) {
        final var platform = parameter.getPlatform();
        final var clientId = parameter.getClientId();
        final var encryptedSecret = textEncryptor.encrypt(parameter.getClientSecret());

        final var clientConstructor = ClientEntity.getConstructor(platform);

        final var client = clientRepository.findByPlatform(platform)
                                             .orElseGet(() -> clientConstructor.f(clientId,encryptedSecret));

        client.setClientSecret(encryptedSecret);

        return clientRepository.save(client).toView().decrypt(textEncryptor);
    }
}
