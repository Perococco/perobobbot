package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.ClientTokenEntity;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.data.jpa.repository.*;
import perobobbot.data.service.TokenService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.TextEncryptor;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

/**
 * @author perococco
 */
@Service
@UnsecuredService
@Transactional
@RequiredArgsConstructor
public class JPATokenService implements TokenService {

    private final @NonNull TextEncryptor textEncryptor;

    private final @NonNull UserRepository userRepository;
    private final @NonNull ClientRepository clientRepository;
    private final @NonNull ViewerIdentityRepository viewerIdentityRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull ClientTokenRepository clientTokenRepository;

    @Override
    public void saveClientToken(@NonNull UUID clientId, @NonNull DecryptedClientToken token) {
        final var encrypted = token.encrypt(textEncryptor);
        final var client = clientRepository.getClientByUuid(clientId);
        final var clientToken = client.addClientToken(encrypted);
        clientTokenRepository.save(clientToken);
    }

    @Override
    public void deleteClientToken(@NonNull UUID tokenId) {
        clientTokenRepository.findByUuid(tokenId)
                             .ifPresent(token -> {
                                 token.getClient().removeClientToken(tokenId);
                                 clientTokenRepository.delete(token);
                             });
    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId) {
        return userTokenRepository.findByUuid(tokenId)
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public void saveUserToken(@NonNull String ownerLogin, @NonNull UUID viewerIdentityId, @NonNull DecryptedUserToken userToken) {
        final var encryptedToken = userToken.encrypt(textEncryptor);
        final var user = userRepository.getByLogin(ownerLogin);
        final var viewerIdentity = viewerIdentityRepository.getByUuid(viewerIdentityId);

        final var userTokenEntity = user.addUserToken(viewerIdentity, encryptedToken);

        userTokenRepository.save(userTokenEntity);
    }

    @Override
    public void deleteUserToken(@NonNull UUID tokenId) {
        userTokenRepository.findByUuid(tokenId)
                           .ifPresent(token -> {
                               token.getOwner().removeUserToken(tokenId);
                               userTokenRepository.delete(token);
                           });

    }

    @Override
    public @NonNull ImmutableList<DecryptedUserTokenView> getUserTokens(@NonNull String ownerLogin) {
        return userTokenRepository.findAllByOwner_Login(ownerLogin)
                                  .stream()
                                  .map(UserTokenEntity::toView)
                                  .map(v -> v.decrypt(textEncryptor))
                                  .collect(ImmutableList.toImmutableList());
    }

}
