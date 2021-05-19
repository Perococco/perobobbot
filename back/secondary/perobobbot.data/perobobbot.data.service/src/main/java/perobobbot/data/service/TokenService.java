package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.UnknownUserToken;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

public interface TokenService {

    int VERSION = 1;

    /**
     * save a client token
     * @param platform the platform of the client
     * @param clientId the client id of the client
     * @param token the token to save
     */
    void saveClientToken(@NonNull Platform platform, @NonNull String clientId, @NonNull DecryptedClientToken token);

    /**
     * Save a client token
     * @param clientId the id of the client in the database
     * @param token the token to save
     */
    void saveClientToken(@NonNull UUID clientId, @NonNull DecryptedClientToken token);

    /**
     * delete a client token based on its UUID
     * @param tokenId the id of the token to remove
     */
    void deleteClientToken(@NonNull UUID tokenId);

    void saveUserToken(@NonNull String ownerLogin, @NonNull UUID viewerIdentityId, @NonNull DecryptedUserToken userToken);

    void deleteUserToken(@NonNull UUID tokenId);

    @NonNull ImmutableList<DecryptedUserTokenView> getUserTokens(@NonNull String ownerLogin);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId);

    default @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId) {
        return findUserToken(tokenId).orElseThrow(() -> new UnknownUserToken(tokenId));
    }

}
