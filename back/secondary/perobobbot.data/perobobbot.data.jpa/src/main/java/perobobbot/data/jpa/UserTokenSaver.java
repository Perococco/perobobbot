package perobobbot.data.jpa;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.data.com.DataException;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.data.jpa.repository.ClientRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.jpa.repository.UserTokenRepository;
import perobobbot.data.jpa.repository.ViewerIdentityRepository;
import perobobbot.lang.*;
import perobobbot.lang.fp.Try0;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.lang.token.EncryptedUserToken;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.Token;
import perobobbot.oauth.UserIdentity;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenSaver {

    public interface Saver {
        @NonNull DecryptedUserTokenView save(@NonNull String login, @NonNull UUID clientId, @NonNull Token token);
    }


    private final @NonNull ClientRepository clientRepository;
    private final @NonNull UserRepository userRepository;
    private final @NonNull ViewerIdentityRepository viewerIdentityRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull TextEncryptor textEncryptor;

    private final @NonNull String login;
    private final @NonNull UUID clientId;
    private final @NonNull Token token;

    private DecryptedClient decryptedClient;
    private UserEntity owner;
    private UserIdentity userIdentity;
    private ViewerIdentityEntity viewerIdentity;
    private EncryptedUserToken encryptedUserToken;
    private UserTokenEntity userToken;
    private DecryptedUserTokenView decryptedUserTokenView;

    private @NonNull DecryptedUserTokenView save() {
        try {
            this.getClientFromRepository();
            this.getOwnerFromLoginFromRepository();
            this.getUserIdentityFromPlatform();
            this.getOrCreateViewerIdentityFromRepository();
            this.buildEncryptedUserToken();
            this.addEncryptedUserTokenToOwner();
            this.saveEncryptedUserTokenIntoRepository();
            this.transformUserTokenToView();

            return decryptedUserTokenView;
        } catch (Throwable t) {
            LOG.error("Could not save token : {} ", t.getMessage());
            LOG.debug(t);
            t.printStackTrace();
            throw t;
        }
    }


    private void getClientFromRepository() {
        this.decryptedClient = this.clientRepository.getClientByUuid(clientId).toDecryptedView(textEncryptor);
    }

    private void getOwnerFromLoginFromRepository() {
        this.owner = this.userRepository.getByLogin(login);
    }

    private void getUserIdentityFromPlatform() {
        final var promise = oAuthManager.getUserIdentity(decryptedClient, token.getAccessToken());
        this.userIdentity = Try0.of(promise.toCompletableFuture()::get)
                                .fSafe()
                                .mapFailure(e -> (e instanceof ExecutionException) ? e.getCause() : e)
                                .mapFailure(e -> new DataException("Could not retrieve user identity", e))
                                .get();
    }

    private void getOrCreateViewerIdentityFromRepository() {
        this.viewerIdentity = viewerIdentityRepository.findByPlatformAndViewerId(decryptedClient.getPlatform(),
                                                              userIdentity.getUserId())
                                                      .orElseGet(this::createViewerIdentity);
    }

    private @NonNull ViewerIdentityEntity createViewerIdentity() {
        final var platform = decryptedClient.getPlatform();
        final var viewerId = userIdentity.getUserId();
        final var login = userIdentity.getLogin();
        return viewerIdentityRepository.save(new ViewerIdentityEntity(platform, viewerId, login));
    }

    private void buildEncryptedUserToken() {
        this.encryptedUserToken = token.toDecryptedUserToken().encrypt(textEncryptor);
    }


    private void addEncryptedUserTokenToOwner() {
        this.userToken = this.owner.setUserToken(viewerIdentity, encryptedUserToken);
    }

    private void saveEncryptedUserTokenIntoRepository() {
        this.userToken = this.userTokenRepository.save(userToken);
    }

    private void transformUserTokenToView() {
        this.decryptedUserTokenView = this.userToken.toView().decrypt(textEncryptor);
    }


    public static @NonNull Saver saver(@NonNull ClientRepository clientRepository,
                                       @NonNull UserRepository userRepository,
                                       @NonNull ViewerIdentityRepository viewerIdentityRepository,
                                       @NonNull UserTokenRepository userTokenRepository,
                                       @NonNull OAuthManager oAuthManager,
                                       @NonNull TextEncryptor textEncryptor) {
        return (login, clientId, token) -> save(clientRepository, userRepository, viewerIdentityRepository,
                userTokenRepository, oAuthManager, textEncryptor, login, clientId,
                token);
    }

    public static @NonNull DecryptedUserTokenView save(@NonNull ClientRepository clientRepository,
                                                       @NonNull UserRepository userRepository,
                                                       @NonNull ViewerIdentityRepository viewerIdentityRepository,
                                                       @NonNull UserTokenRepository userTokenRepository,
                                                       @NonNull OAuthManager oAuthManager,
                                                       @NonNull TextEncryptor textEncryptor,
                                                       @NonNull String login,
                                                       @NonNull UUID clientId,
                                                       @NonNull Token token) {
        return new UserTokenSaver(clientRepository, userRepository, viewerIdentityRepository, userTokenRepository,
                oAuthManager, textEncryptor, login, clientId, token).save();
    }

}
