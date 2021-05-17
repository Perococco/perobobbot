package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;

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
