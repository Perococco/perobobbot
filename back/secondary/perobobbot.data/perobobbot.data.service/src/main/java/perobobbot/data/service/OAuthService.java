package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.Token;
import perobobbot.oauth.UserOAuthInfo;

import java.util.Optional;
import java.util.UUID;

public interface OAuthService {

    int VERSION = 1;

    /**
     * @param tokenId the id of the searched token
     * @return an optional containing the client token associated with the provided <code>tokenId</code>, or an empty if none could be found
     */
    @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull UUID tokenId);

    /**
     * @param platform the platform of the searched token
     * @return an optional containing the client token for the provided <code>platform</code>, or an empty if none could be found
     */
    @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform);

    @NonNull DecryptedClientTokenView findOrAuthenticateClientToken(@NonNull Platform platform);

    @NonNull DecryptedClientTokenView authenticateClient(@NonNull Platform platform);


    /**
     *
     * @param login the login of the user
     * @return the list of all token associated with the user with the provided <code>login</code>
     */
    @NonNull ImmutableSet<DecryptedUserTokenView> getAllUserTokens(@NonNull String login);

    @NonNull Optional<DecryptedUserTokenView> findUserMainToken(@NonNull String login, @NonNull Platform platform);
    @NonNull Optional<DecryptedUserTokenView> findUserMainToken(@NonNull String login, @NonNull Platform platform, @NonNull Scope requiredScope);

    @NonNull ImmutableList<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform);
    @NonNull ImmutableList<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull Scope requiredScope);

    @NonNull Optional<DecryptedUserTokenView> findUserTokenByViewerId(String broadcasterId, Platform platform);
    @NonNull Optional<DecryptedUserTokenView> findUserTokenByViewerId(String broadcasterId, Platform platform, Scope requiredScope);


    void updateUserToken(@NonNull String login, @NonNull Platform platform, @NonNull String viewerId, @NonNull Token token);

    @NonNull UserOAuthInfo<DecryptedUserTokenView> createUserToken(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId);

    @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId);

    @NonNull DecryptedUserTokenView refreshUserToken(@NonNull UUID tokenId);

    @NonNull DecryptedUserTokenView setUserTokenAsMain(@NonNull UUID tokenId);

    @NonNull DecryptedUserTokenView setUserTokenAsNotMain(@NonNull UUID tokenId);

    void deleteUserToken(@NonNull UUID tokenId);

    void deleteClientToken(@NonNull UUID uuid);

}
