package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.domain.ClientEntity;
import perobobbot.data.domain.base.ClientEntityBase;
import perobobbot.data.jpa.repository.ClientRepository;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.DecryptedClient;
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
        return clientRepository.findFirstByPlatform(platform)
                               .map(ClientEntityBase::toView)
                               .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull Optional<DecryptedClient> findClient(@NonNull Platform platform, @NonNull String clientId) {
        return clientRepository.findByPlatformAndClientId(platform, clientId)
                               .map(ClientEntityBase::toView)
                               .map(t -> t.decrypt(textEncryptor));
    }


    @Override
    public @NonNull ImmutableList<DecryptedClient> findAllClients() {
        return clientRepository.findAll()
                               .stream()
                               .map(ClientEntityBase::toView)
                               .map(t -> t.decrypt(textEncryptor))
                               .collect(ImmutableList.toImmutableList());
    }

    @Override
    @Transactional
    public @NonNull DecryptedClient createClient(@NonNull CreateClientParameter parameter) {
        final var encryptedSecret = textEncryptor.encrypt(parameter.getClientSecret());

        final var client = clientRepository.findByPlatformAndClientId(parameter.getPlatform(),
                                                                        parameter.getClientId())
                                             .orElseGet(() -> new ClientEntity(parameter.getPlatform(),
                                                                               parameter.getClientId(),
                                                                               encryptedSecret));
        client.setClientSecret(encryptedSecret);

        return clientRepository.save(client).toView().decrypt(textEncryptor);
    }
}
