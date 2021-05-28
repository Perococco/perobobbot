package perobobbot.data.service;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.UserOAuthInfo;

import java.util.Optional;
import java.util.UUID;

public interface OAuthService {

    int VERSION = 1;

    /**
     * @return all the available clients
     */
    @NonNull ImmutableSet<Client> getClients();

    /**
     * @param tokenId the id of the searched token
     * @return an optional containing the client token associated with the provided <code>tokenId</code>, or an empty if none could by found
     */
    @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull UUID tokenId);

    @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform);

    @NonNull DecryptedClientTokenView authenticateClient(@NonNull UUID clientId);


    @NonNull ImmutableSet<DecryptedUserTokenView> getAllUserTokens(@NonNull String login);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull Scope requiredScope);

    @NonNull UserOAuthInfo<DecryptedUserTokenView> authenticateUser(@NonNull String login, @NonNull ImmutableSet<? extends Scope> scopes, @NonNull Platform platform);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId);

    @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId);

    void deleteUserToken(@NonNull UUID tokenId);
}
