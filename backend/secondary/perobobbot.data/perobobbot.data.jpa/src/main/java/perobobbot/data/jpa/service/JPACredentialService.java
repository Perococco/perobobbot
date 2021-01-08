package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.com.DuplicateUser;
import perobobbot.data.com.User;
import perobobbot.data.domain.CredentialEntity;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.jpa.repository.CredentialRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.UserProvider;
import perobobbot.data.service.UserService;
import perobobbot.lang.Credential;
import perobobbot.lang.PasswordEncoder;
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
    public @NonNull ImmutableList<DataCredentialInfo> getCredentials(@NonNull String login) {
        return userRepository.getByLogin(login)
                             .credentials()
                             .map(CredentialEntity::toView)
                             .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull Optional<DataCredentialInfo> getCredential(@NonNull String login, @NonNull Platform platform) {
        return credentialRepository.findByOwner_LoginAndPlatform(login, platform).map(CredentialEntity::toView);
    }

    @Override
    @Transactional
    public @NonNull DataCredentialInfo updateCredential(@NonNull String login, @NonNull Platform platform, @NonNull Credential credential) {
        final var credentialEntity = credentialRepository.findByOwner_LoginAndPlatformAndNick(login, platform, credential.getNick())
                                                         .orElseGet(() -> createNewCredential(login, platform, credential.getNick()));
        credentialEntity.setSecret(credential.getSecret());
        return credentialRepository.save(credentialEntity).toView();
    }

    @Override
    public @NonNull Optional<DataCredentialInfo> findCredential(@NonNull UUID uuid) {
        return credentialRepository.findByUuid(uuid).map(CredentialEntity::toView);
    }

    @Override
    @Transactional
    public void deleteCredential(@NonNull UUID id) {
        final var credential = credentialRepository.findByUuid(id);
        credential.ifPresent(credentialRepository::delete);
    }

    private @NonNull CredentialEntity createNewCredential(@NonNull String login, @NonNull Platform platform, @NonNull String nick) {
        return userRepository.getByLogin(login).addCredential(platform,nick);
    }

}
