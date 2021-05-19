package perobobbot.data.service;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.data.com.AuthenticateUserParameter;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

public interface OAuthService {

    int VERSION = 1;

    @NonNull ImmutableSet<Client> getClients();

    @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull UUID tokenId);

    @NonNull Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform, @NonNull String clientId);

    @NonNull DecryptedClientTokenView authenticateClient(@NonNull UUID clientId);

    @NonNull ImmutableSet<DecryptedUserTokenView> getAllUserTokens(@NonNull String login);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull UUID viewerIdentityId);

    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull String viewerId);

    @NonNull DecryptedUserTokenView authenticateUser(@NonNull AuthenticateUserParameter parameter, @NonNull UUID clientId);


    @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId);

    @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId);

    void deleteUserToken(@NonNull UUID tokenId);
}
