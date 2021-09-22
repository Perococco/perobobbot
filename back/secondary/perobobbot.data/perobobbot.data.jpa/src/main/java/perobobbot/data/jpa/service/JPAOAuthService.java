package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import perobobbot.data.domain.ClientEntity;
import perobobbot.data.domain.ClientTokenEntity;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.data.jpa.UserTokenSaver;
import perobobbot.data.jpa.repository.*;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.*;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author perococco
 */
@Service
@UnsecuredService
@Transactional
public class JPAOAuthService implements OAuthService {

    private final @NonNull TransactionTemplate transactionTemplate;

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull TextEncryptor textEncryptor;

    private final @NonNull ClientRepository clientRepository;

    private final @NonNull UserRepository userRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull ClientTokenRepository clientTokenRepository;

    private final @NonNull Instants instants;

    private final @NonNull UserTokenSaver.Saver userTokenSaver;


    public JPAOAuthService(@NonNull PlatformTransactionManager platformTransactionManager,
                           @NonNull OAuthManager oAuthManager,
                           @NonNull TextEncryptor textEncryptor,
                           @NonNull UserRepository userRepository,
                           @NonNull ClientRepository clientRepository,
                           @NonNull ViewerIdentityRepository viewerIdentityRepository,
                           @NonNull UserTokenRepository userTokenRepository,
                           @NonNull ClientTokenRepository clientTokenRepository,
                           @NonNull Instants instants) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.oAuthManager = oAuthManager;
        this.textEncryptor = textEncryptor;
        this.clientRepository = clientRepository;
        this.userTokenRepository = userTokenRepository;
        this.clientTokenRepository = clientTokenRepository;
        this.instants = instants;
        this.userRepository = userRepository;
        this.userTokenSaver = UserTokenSaver.saver(clientRepository, userRepository, viewerIdentityRepository,
                userTokenRepository, oAuthManager, textEncryptor);
    }

    @Override
    public @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull UUID tokenId) {
        return clientTokenRepository.findByUuid(tokenId)
                                    .map(ClientTokenEntity::toView)
                                    .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform) {
        return clientTokenRepository.findByClient_Platform(platform)
                                    .map(ClientTokenEntity::toView)
                                    .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull DecryptedClientTokenView findOrAuthenticateClientToken(@NonNull Platform platform) {
        return findClientToken(platform).orElseGet(() -> authenticateClient(platform));
    }

    @Override
    public @NonNull DecryptedClientTokenView authenticateClient(@NonNull Platform platform) {
        final var client = clientRepository.getFirstByPlatform(platform);
        final var oauthController = oAuthManager.getController(platform);

        try {
            final var token = oauthController.getClientToken(client.toView().decrypt(textEncryptor))
                                             .toCompletableFuture()
                                             .get();

            return saveClientToken(client, token);
        } catch (ExecutionException e) {
            throw new OAuthFailure(platform, client.getClientId(), e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OAuthInterrupted(platform, client.getClientId());
        }

    }

    @Override
    public @NonNull ImmutableList<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform) {
        return userTokenRepository.findByOwner_LoginAndViewerIdentity_Platform(login, platform)
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor))
                                  .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull ImmutableList<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull Scope requiredScope) {
        return userTokenRepository.findByOwner_LoginAndViewerIdentity_PlatformAndScopesContains(login, platform,
                requiredScope.getName())
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor))
                                  .collect(ImmutableList.toImmutableList());

    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserMainToken(@NonNull String login, @NonNull Platform platform) {
        return userTokenRepository.findByOwner_LoginAndMainIsTrueAndViewerIdentity_Platform(login, platform)
                                  .map(e -> e.toDecryptedView(textEncryptor));
    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserMainToken(@NonNull String login, @NonNull Platform platform, @NonNull Scope requiredScope) {
        return userTokenRepository.findByOwner_LoginAndMainIsTrueAndViewerIdentity_PlatformAndScopesContains(login, platform, requiredScope.getName())
                                  .map(e -> e.toDecryptedView(textEncryptor));
    }

    @Override
    public @NonNull DecryptedUserTokenView setUserTokenAsMain(@NonNull UUID tokenId) {
        final var token = userTokenRepository.getByUuid(tokenId);

        {
            final var allTokensOfUser = userTokenRepository.findAllByOwner_Login(token.getOwner().getLogin());
            allTokensOfUser.forEach(t -> {
                t.setMain(t.getUuid().equals(tokenId));
            });
            userTokenRepository.saveAll(allTokensOfUser);
        }

        return userTokenRepository.save(token).toDecryptedView(textEncryptor);
    }

    @Override
    public @NonNull DecryptedUserTokenView setUserTokenAsNotMain(@NonNull UUID tokenId) {
        final var token = userTokenRepository.getByUuid(tokenId);
        token.setMain(false);
        return userTokenRepository.save(token).toDecryptedView(textEncryptor);
    }

    @Override
    public @NonNull ImmutableSet<DecryptedUserTokenView> getAllUserTokens(@NonNull String login) {
        return userTokenRepository.findAllByOwner_Login(login)
                                  .stream()
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor))
                                  .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public @NonNull UserOAuthInfo<DecryptedUserTokenView> createUserToken(@NonNull String login,
                                                                          @NonNull ImmutableSet<? extends Scope> scopes,
                                                                          @NonNull Platform platform) {
        final var client = clientRepository.getFirstByPlatform(platform).toView().decrypt(textEncryptor);

        final var userOAuthInfo = oAuthManager.prepareUserOAuth(client, scopes);

        return userOAuthInfo.then(
                token -> transactionTemplate.execute(status -> userTokenSaver.save(login, client.getId(), token)));
    }

    private @NonNull DecryptedClientTokenView saveClientToken(@NonNull ClientEntity client, @NonNull Token token) {
        final var decryptedClientToken = DecryptedClientToken.builder()
                                                             .accessToken(token.getAccessToken())
                                                             .duration(token.getDuration())
                                                             .expirationInstant(token.getExpirationInstant())
                                                             .build();
        final var clientTokenEntity = client.addClientToken(decryptedClientToken.encrypt(textEncryptor));

        return clientTokenRepository.save(clientTokenEntity)
                                    .toView()
                                    .decrypt(textEncryptor);
    }

    @Override
    public @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId) {
        return userTokenRepository
                .getByUuid(tokenId)
                .toView()
                .decrypt(textEncryptor);
    }

    @Override
    public @NonNull DecryptedUserTokenView refreshUserToken(@NonNull UUID tokenId) {
        final var tokenEntity = userTokenRepository.getByUuid(tokenId);
        final var platform = tokenEntity.getPlatform();
        final var client = clientRepository.getFirstByPlatform(platform).toDecryptedView(textEncryptor);

        final var refreshToken = textEncryptor.decrypt(tokenEntity.getRefreshToken());

        final Throwable error;
        try {
            final var refreshedToken = oAuthManager.refreshToken(client, refreshToken)
                                                   .toCompletableFuture()
                                                   .get();
            tokenEntity.setAccessToken(textEncryptor.encrypt(refreshedToken.getAccessToken()));
            tokenEntity.setRefreshToken(textEncryptor.encrypt(refreshedToken.getRefreshToken()));
            tokenEntity.setExpirationInstant(instants.now().plusSeconds(tokenEntity.getDuration()));
            return userTokenRepository.save(tokenEntity).toDecryptedView(textEncryptor);

        } catch (ExecutionException e) {
            error = e.getCause();
        } catch (Throwable e) {
            error = e;
        }

        //todo remove invalid token on 400/401 error code
        ThrowableTool.interruptThreadIfCausedByInterruption(error);
        throw new OAuthRefreshFailed(platform, client.getClientId(), error);

    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId) {
        return userTokenRepository.findByUuid(tokenId)
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public void deleteClientToken(@NonNull UUID id) {
        final var token = clientTokenRepository.getByUuid(id);
        //todo delete all client token associated with the removed client
        clientTokenRepository.delete(token);
    }

    @Override
    public void deleteUserToken(@NonNull UUID tokenId) {
        final var token = userTokenRepository.getByUuid(tokenId);
        final var decryptedAccessToken = textEncryptor.decrypt(token.getAccessToken());

        final var platform = token.getPlatform();
        final var client = clientRepository.getFirstByPlatform(platform).toDecryptedView(textEncryptor);

        oAuthManager.getController(token.getPlatform())
                    .revokeToken(client, decryptedAccessToken);

        token.getOwner().removeUserToken(tokenId);
        userRepository.save(token.getOwner());
        userTokenRepository.delete(token);

    }
}
