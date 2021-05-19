package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.AuthenticateUserParameter;
import perobobbot.data.domain.*;
import perobobbot.data.domain.base.ClientEntityBase;
import perobobbot.data.jpa.repository.*;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.*;
import perobobbot.lang.fp.Value2;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author perococco
 */
@Service
@UnsecuredService
@Transactional
@RequiredArgsConstructor
public class JPAOAuthService implements OAuthService {

    private static final ExecutorService OAUTH_EXECUTOR_SERVICE = Executors.newCachedThreadPool(
            ThreadFactories.daemon("OAuth Callback thread"));

    private final @NonNull OAuthManager oAuthManager;

    private final @NonNull TextEncryptor textEncryptor;

    private final @NonNull UserRepository userRepository;
    private final @NonNull ClientRepository clientRepository;
    private final @NonNull ViewerIdentityRepository viewerIdentityRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull ClientTokenRepository clientTokenRepository;


    @Override
    public @NonNull ImmutableSet<Client> getClients() {
        return clientRepository.findAll()
                               .stream()
                               .map(ClientEntityBase::toView)
                               .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull UUID tokenId) {
        return clientTokenRepository.findByUuid(tokenId)
                                    .map(ClientTokenEntity::toView)
                                    .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform, @NonNull String clientId) {
        return clientTokenRepository.findByPlatformAndClient_ClientId(platform, clientId)
                                    .map(ClientTokenEntity::toView)
                                    .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull DecryptedClientTokenView authenticateClient(@NonNull UUID clientId) {
        final var client = clientRepository.getClientByUuid(clientId);
        final var platform = client.getPlatform();
        final var oauthController = oAuthManager.getController(platform);

        try {
            final var token = oauthController.getClientToken(client.toView())
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
    public @NonNull ImmutableSet<DecryptedUserTokenView> getAllUserTokens(@NonNull String login) {
        return userTokenRepository.findAllByOwner_Login(login)
                                  .stream()
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor))
                                  .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull UUID viewerIdentityId) {
        return userTokenRepository.findByOwner_LoginAndViewerIdentity_Id(login, viewerIdentityId)
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull String viewerId) {
        return userTokenRepository.findByOwner_LoginAndPlatformAndViewerIdentity_ViewerId(login, platform, viewerId)
                                  .map(UserTokenEntity::toView)
                                  .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public @NonNull DecryptedUserTokenView authenticateUser(@NonNull AuthenticateUserParameter parameter, @NonNull UUID clientId) {
        final var client = clientRepository.getClientByUuid(clientId).toView();
        final var platform = client.getPlatform();
        final var owner = userRepository.getByLogin(parameter.getLogin());
        final var oauthController = oAuthManager.getController(platform);
        var userOAuthInfo = oauthController.prepareUserOAuth(client, parameter.getScopes());

        OAUTH_EXECUTOR_SERVICE.submit(() -> {
            parameter.getUriCallable().accept(userOAuthInfo.getOauthURI());
        });


        final Value2<Token,UserIdentity> oauthResult;
        try {
            oauthResult = userOAuthInfo.getFutureToken()
                                       .thenCompose(
                                               token -> {
                                                   return oauthController.getUserIdentity(client,
                                                                                          token.getAccessToken())
                                                                         .thenApply(userIdentity -> Value2.of(token,
                                                                                                              userIdentity));
                                               })
                                       .toCompletableFuture()
                                       .get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OAuthInterrupted(platform,client.getClientId());
        } catch (ExecutionException e) {
            throw new OAuthFailure(platform,client.getClientId(),e.getCause());
        }

        final var identity = oauthResult.getSecond();
        final var token = oauthResult.getFirst();

        final var viewerIdentity = getOrCreateViewerIdentity(platform, identity);

        return saveUserToken(owner, viewerIdentity, token);
    }

    private @NonNull ViewerIdentityEntity getOrCreateViewerIdentity(@NonNull Platform platform, @NonNull UserIdentity userIdentity) {
        final String viewerId = userIdentity.getUserId();
        final String pseudo = userIdentity.getLogin();
        final Supplier<ViewerIdentityEntity> viewerIdentityEntityFactory = () -> viewerIdentityRepository.save(
                new ViewerIdentityEntity(platform, viewerId, pseudo));

        return viewerIdentityRepository.findByPlatformAndViewerId(platform, userIdentity.getUserId())
                                       .orElseGet(viewerIdentityEntityFactory);

    }


    private @NonNull DecryptedUserTokenView saveUserToken(@NonNull UserEntity owner, @NonNull ViewerIdentityEntity viewerIdentity, @NonNull Token token) {
        final var decryptedUserToken = DecryptedUserToken.builder()
                                                         .accessToken(token.getAccessToken())
                                                         .duration(token.getDuration())
                                                         .expirationInstant(token.getExpirationInstant())
                                                         .refreshToken(token.getRefreshToken().orElse(Secret.empty()))
                                                         .scopes(token.getScopes())
                                                         .build();

        final var userToken = owner.addUserToken(viewerIdentity, decryptedUserToken.encrypt(textEncryptor));

        return userTokenRepository.save(userToken)
                                  .toView()
                                  .decrypt(textEncryptor);
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
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId) {
        return userTokenRepository.findByUuid(tokenId)
                                  .map(t -> t.toView())
                .map(t -> t.decrypt(textEncryptor));
    }

    @Override
    public void deleteUserToken(@NonNull UUID tokenId) {
        final var token = userTokenRepository.getByUuid(tokenId);
        token.getOwner().removeUserToken(tokenId);
        userTokenRepository.delete(token);
    }
}
