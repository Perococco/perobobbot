package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.security.com.User;
import perobobbot.data.domain.CredentialEntity;
import perobobbot.data.jpa.repository.CredentialRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Perococco
 */
@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPACredentialService implements CredentialService {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final CredentialRepository credentialRepository;

    @Override
    public @NonNull ImmutableList<DataCredentialInfo> getUserCredentials(@NonNull String login) {
        return userRepository.getByLogin(login)
                             .credentials()
                             .map(CredentialEntity::toView)
                             .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull DataCredentialInfo createCredential(@NonNull String login, @NonNull Platform platform) {
        final var credentials = userRepository.getByLogin(login)
                                              .addCredential(platform);
        return credentialRepository.save(credentials).toView();
    }

    @Override
    public @NonNull Optional<DataCredentialInfo> findCredential(@NonNull UUID id) {
        return credentialRepository.findByUuid(id).map(CredentialEntity::toView);
    }

    @Override
    @Transactional
    public @NonNull DataCredentialInfo updateCredential(@NonNull UUID id, @NonNull Credential credential) {
        final var credentialEntity = credentialRepository.getByUuid(id);
        credentialEntity.setNick(credential.getNick());
        credentialEntity.setSecret(credential.getSecret());
        return credentialRepository.save(credentialEntity).toView();
    }

    @Override
    @Transactional
    public void deleteCredential(@NonNull UUID id) {
        final var credential = credentialRepository.findByUuid(id);
        credential.ifPresent(credentialRepository::delete);
    }

}
