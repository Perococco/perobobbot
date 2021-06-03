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
     * Request a Client Token. The client id and secret are provided by configuration
     * @return a completionStage the completion result is the requested token
     */
    @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient client);

    @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken);

    @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret refreshToken);

    @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken);

    @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull DecryptedClient client, @NonNull Secret accessToken);
}
