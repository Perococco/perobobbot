package perobobbot.data.jpa.repository.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.DataException;
import perobobbot.data.jpa.repository.ClientRepository;
import perobobbot.lang.*;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.fp.Try0;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.Token;

import java.util.UUID;

@RequiredArgsConstructor
public class UserIdentityRetriever {

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull ClientRepository clientRepository;
    private final @NonNull TextEncryptor textEncryptor;

    public @NonNull UserIdentity retrieveUserIdentity(@NonNull UUID clientId, @NonNull Token token) {
        final var decryptedClient = getClient(clientId);
        return retrieveUserIdentityFromPlatform(decryptedClient.getPlatform(),token);
    }

    private @NonNull DecryptedClient getClient(@NonNull UUID clientId) {
        return this.clientRepository.getClientByUuid(clientId).toDecryptedView(textEncryptor);
    }

    private @NonNull UserIdentity retrieveUserIdentityFromPlatform(@NonNull Platform platform, @NonNull Token token) {
        final var promise = oAuthManager.getUserIdentity(platform, token.getAccessToken());
        return Try0.of(promise.toCompletableFuture()::get)
                                .fSafe()
                                .mapFailure(ThrowableTool::getCauseIfExecutionException)
                                .mapFailure(e -> new DataException("Could not retrieve user identity", e))
                                .get();
    }

}
