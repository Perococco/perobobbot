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
     * @return the URI to use to perform the OAuth Authorization Code Flow
     */
    @NonNull UserOAuthInfo prepareUserOAuth(ImmutableSet<? extends Scope> scopes);

    @NonNull CompletionStage<Token> getAppToken();

    @NonNull CompletionStage<?> revokeToken(@NonNull String accessToken);

    @NonNull CompletionStage<Token> refreshToken(@NonNull Token expiredToken);

    @NonNull CompletionStage<?> validateToken(@NonNull Token token);

    default @NonNull OAuthTokenRefresher createOAuthTokenRefresher() {
        return this::refreshToken;
    }
}
