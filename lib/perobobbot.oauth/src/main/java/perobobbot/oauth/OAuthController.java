package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import perobobbot.lang.Nil;
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
     * @param clientId the id of the client used for the OAuth
     * @return the URI to use to perform the OAuth Authorization Code Flow
     */
    @NonNull UserOAuthInfo prepareUserOAuth(@NonNull String clientId, @NonNull Secret clientSecret, ImmutableSet<? extends Scope> scopes);

    @NonNull CompletionStage<Token> getAppToken(@NonNull String clientId, @NonNull Secret clientSecret, ImmutableSet<? extends Scope> scopes);

    @NonNull CompletionStage<?> revokeToken(@NonNull String clientId, @NonNull String accessToken);

    @NonNull CompletionStage<Token> refreshToken(@NonNull String clientId, @NonNull Secret clientSecret, @NonNull Token expiredToken);

    @NonNull CompletionStage<?> validateToken(@NonNull Token token);

    default @NonNull OAuthTokenRefresher createOAuthTokenRefresher(@NonNull String clientId, @NonNull Secret clientSecret) {
        return token -> refreshToken(clientId,clientSecret,token);
    }
}
