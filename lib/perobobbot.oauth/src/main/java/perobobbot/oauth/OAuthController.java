package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Client;
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
    @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull Client client, ImmutableSet<? extends Scope> scopes);

    /**
     * Request a Client Token. The client id and secret are provided by configuration
     * @return a completionStage the completion result is the requested token
     */
    @NonNull CompletionStage<Token> getClientToken(@NonNull Client client);

    @NonNull CompletionStage<?> revokeToken(@NonNull Client client, @NonNull Secret accessToken);

    @NonNull CompletionStage<Token> refreshToken(@NonNull Client client, @NonNull Token expiredToken);

    @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken);

    @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull Client client, @NonNull Secret accessToken);
}
