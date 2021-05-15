package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.client.api.TokenRequired;
import perobobbot.twitch.client.api.TwitchService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Intercept all calls to the {@link perobobbot.twitch.client.api.TwitchService}
 * and push the token type to use by the webclient filter {@link perobobbot.twitch.client.webclient.OAuthTokenFilter}
 * that sets the correct OAuth token
 */
@RequiredArgsConstructor
public class OAuthTokenTypeInterceptor implements InvocationHandler {

    private final @NonNull TwitchService twitchService;

    private final @NonNull TokenTypeManager tokenTypeManager;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final var delegateMethod = twitchService.getClass().getMethod(method.getName(), method.getParameterTypes());
        final var tokenRequired = delegateMethod.getAnnotation(TokenRequired.class);

        if (tokenRequired != null) {
            tokenTypeManager.push(tokenRequired.type());
        }
        try {
            return delegateMethod.invoke(twitchService, args);
        } finally {
            if (tokenRequired != null) {
                tokenTypeManager.pop();
            }
        }
    }
}
