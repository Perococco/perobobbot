package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.UnknownUserToken;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

public interface TokenService {

    int VERSION = 1;

    void saveClientToken(@NonNull UUID clientId, @NonNull DecryptedClientToken token);

    void deleteClientToken(@NonNull UUID tokenId);

    void saveUserToken(@NonNull String ownerLogin, @NonNull UUID viewerIdentityId, @NonNull DecryptedUserToken userToken);

    void deleteUserToken(@NonNull UUID tokenId);

    @NonNull ImmutableList<DecryptedUserTokenView> getUserTokens(@NonNull String ownerLogin);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId);

    default @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId) {
        return findUserToken(tokenId).orElseThrow(() -> new UnknownUserToken(tokenId));
    }

}
