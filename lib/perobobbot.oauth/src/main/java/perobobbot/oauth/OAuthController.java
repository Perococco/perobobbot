package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;

import java.util.concurrent.CompletionStage;

public interface OAuthController {
    
    /**
     * @return the platform this OAuth controller applies to
     */
    @NonNull Platform getPlatform();

    /**
     * @return the URI to use to perform the OAuth Authorization Code Flow
     */
    @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, ImmutableSet<? extends Scope> scopes);

    /**
     * @return the URI to use to perform the OAuth Authorization Code Flow
     */
    default @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client) {
        return prepareUserOAuth(client,getDefaultScopes());
    }

    /**
     * Request a Client Token. The client id and secret are provided by configuration
     * @return a completionStage the completion result is the requested token
     */
    @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient client);

    /**
     * @param client the client information
     * @param accessToken the token to revoke
     * @return a completionStage that completes when the revocation is done
     */
    @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken);

    /**
     * @param client the client information the token is associated to
     * @param tokenToRefresh the token to refresh
     * @return a completionStage containing the refreshed token that completes when the refreshing is done
     */
    @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret tokenToRefresh);

    @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken);

    @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull DecryptedClient client, @NonNull Secret accessToken);

    @NonNull ImmutableSet<? extends Scope> getDefaultScopes();

    void dispose();
}
