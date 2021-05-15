package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import perobobbot.lang.Instants;
import perobobbot.lang.Todo;
import perobobbot.lang.fp.Try0;
import perobobbot.lang.fp.Try2;
import perobobbot.lang.fp.TryResult;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthTokenProvider;
import perobobbot.oauth.OAuthTokenRefresher;
import perobobbot.oauth.Token;
import perobobbot.twitch.client.api.TokenType;
import perobobbot.twitch.client.api.TokenTypeProvider;
import perobobbot.twitch.client.api.TwitchService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class ExpiredOAuthTokenInterceptor implements InvocationHandler {

    private final @NonNull Instants instants;
    private final @NonNull TokenTypeProvider tokenTypeProvider;
    private final @NonNull TwitchService twitchService;
    private final @NonNull OAuthTokenProvider oAuthTokenProvider;
    private final @NonNull OAuthTokenRefresher oAuthTokenRefresher;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Throwable error;
        try {
            return performCall(method, args);
        } catch (Throwable t) {
            error = t;
        }
        final var token = retrieveTokenToRefresh(error);
        if (token.getExpirationInstant().isBefore(instants.now())) {
            throw error;
        }

        oAuthTokenRefresher.refreshToken(token)
                           .toCompletableFuture()
                           .get();

        return performCall(method, args);
    }

    private Token retrieveTokenToRefresh(@NonNull Throwable throwable) throws Throwable {
        if (!(throwable instanceof WebClientResponseException)) {
            throw throwable;
        }
        final var webclientException = (WebClientResponseException) throwable;
        if (webclientException.getStatusCode() != HttpStatus.UNAUTHORIZED) {
            throw throwable;
        }

        if (tokenTypeProvider.getTokenType() != TokenType.USER_TOKEN) {
            throw throwable;
        }
        return getUserToken();
    }

    private Token getUserToken() {
        return oAuthTokenProvider.getUserToken()
                                 .orElseThrow(
                                         () -> new IllegalStateException("Could not get the User token to refresh"));
    }


    private @NonNull Object performCall(Method method, Object[] args) throws Throwable {
        return method.invoke(twitchService, args);
    }
}
