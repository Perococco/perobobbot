package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;

import java.util.concurrent.CompletionStage;

public interface OAuthController {

    interface Factory {
        @NonNull Platform getPlatform();
        @NonNull OAuthController createOAuthController(@NonNull Client client);
    }

    /**
     * @return the platform this OAuth controller applies to
     */
    @NonNull Platform getPlatform();

    /**
     * @return the URI to use to perform the OAuth Authorization Code Flow
     */
    @NonNull UserOAuthInfo prepareUserOAuth(ImmutableSet<? extends Scope> scopes);

    /**
     * Request a Client Token. The client id and secret are provided by configuration
     * @return a completionStage the completion result is the requested token
     */
    @NonNull CompletionStage<Token> getClientToken();

    @NonNull CompletionStage<?> revokeToken(@NonNull Secret accessToken);

    @NonNull CompletionStage<Token> refreshToken(@NonNull Token expiredToken);

    @NonNull CompletionStage<?> validateToken(@NonNull Token token);

}
