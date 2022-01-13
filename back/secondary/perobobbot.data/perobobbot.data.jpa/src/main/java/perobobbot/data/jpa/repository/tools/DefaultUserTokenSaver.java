package perobobbot.data.jpa.repository.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.data.domain.PlatformUserEntity;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.jpa.repository.UserTokenRepository;
import perobobbot.lang.TextEncryptor;
import perobobbot.lang.UserIdentity;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.lang.token.EncryptedUserToken;
import perobobbot.oauth.Token;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
public class DefaultUserTokenSaver implements UserTokenSaver {

    private final @NonNull UserRepository userRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull UserIdentityRetriever userIdentityRetriever;
    private final @NonNull TextEncryptor textEncryptor;
    private final @NonNull PlatformUserHelper platformUserHelper;


    @Override
    public @NonNull DecryptedUserTokenView save(@NonNull String login, @NonNull UUID clientId, @NonNull Token token) {
        final var userIdentity = userIdentityRetriever.retrieveUserIdentity(clientId, token);
        final var helper = platformUserHelper.helperForIdentity(userIdentity);

        return new Executor<>(helper, login, token).execute();
    }

    @RequiredArgsConstructor
    private class Executor<I extends UserIdentity, T extends PlatformUserEntity<I>> {

        private final @NonNull PlatformIdentityHelper<I, T> helper;
        private final @NonNull String login;
        private final @NonNull Token token;

        private long nbExistingTokens;
        private UserEntity owner;
        private T platformUser;
        private EncryptedUserToken encryptedUserToken;
        private UserTokenEntity userToken;

        private @NonNull DecryptedUserTokenView execute() {
            this.getNumberOfExistingToken();
            this.getTokenOwner();
            this.getOrCreatePlatformUser();
            this.createEncryptedUserToken();

            this.createUserToken();
            this.setTokenAsMainIfNoneExistForThePlatform();
            this.saveModifiedEntities();

            return userToken.toView().decrypt(textEncryptor);
        }

        private void getNumberOfExistingToken() {
            this.nbExistingTokens = userTokenRepository.countDistinctByPlatformUser_Platform(helper.getPlatform());
        }

        private void getTokenOwner() {
            this.owner = userRepository.getByLogin(login);
        }

        private void getOrCreatePlatformUser() {
            this.platformUser = helper.getOrCreatePlatformUser();
        }

        private void createEncryptedUserToken() {
            this.encryptedUserToken = token.toDecryptedUserToken().encrypt(textEncryptor);
        }


        private void createUserToken() {
            this.userToken = owner.setUserToken(platformUser, encryptedUserToken);
        }

        private void setTokenAsMainIfNoneExistForThePlatform() {
            this.userToken.setMain(nbExistingTokens == 0);
        }


        private void saveModifiedEntities() {
            userTokenRepository.save(userToken);
        }


    }

}
