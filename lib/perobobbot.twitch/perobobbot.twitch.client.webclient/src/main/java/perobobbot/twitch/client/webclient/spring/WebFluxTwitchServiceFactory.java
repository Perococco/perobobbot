package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Instants;
import perobobbot.oauth.OAuthTokenProvider;
import perobobbot.oauth.OAuthTokenRefresher;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceFactory;
import perobobbot.twitch.client.webclient.OAuthTokenFilter;
import perobobbot.twitch.client.webclient.WebClientAppTwitchService;

import java.lang.reflect.Proxy;

@RequiredArgsConstructor
public class WebFluxTwitchServiceFactory implements TwitchServiceFactory {

    private final @NonNull Instants instants;
    private final @NonNull TokenTypeManager tokenTypeManager;
    private final @NonNull OAuthTokenRefresher oAuthTokenRefresher;

    @Override
    public @NonNull TwitchService create(@NonNull OAuthTokenProvider oAuthTokenProvider) {
        final var nakedService = createNakedService(oAuthTokenProvider);
        return interceptToGetTokenType(interceptForExpiredToken(nakedService, oAuthTokenProvider));
    }

    private @NonNull TwitchService createNakedService(@NonNull OAuthTokenProvider oAuthTokenProvider) {
        final var oauthFilter = new OAuthTokenFilter(tokenTypeManager, oAuthTokenProvider);
        final var webclient = WebClient.builder().filter(oauthFilter).build();
        return new WebClientAppTwitchService(webclient);
    }

    private @NonNull TwitchService interceptToGetTokenType(@NonNull TwitchService twitchService) {
        return (TwitchService) Proxy.newProxyInstance(
                twitchService.getClass().getClassLoader(),
                new Class[]{TwitchService.class},
                new OAuthTokenTypeInterceptor(twitchService, tokenTypeManager));
    }

    private @NonNull TwitchService interceptForExpiredToken(@NonNull TwitchService twitchService, @NonNull OAuthTokenProvider oAuthTokenProvider) {
        return (TwitchService) Proxy.newProxyInstance(
                twitchService.getClass().getClassLoader(),
                new Class[]{TwitchService.class},
                new ExpiredOAuthTokenInterceptor(instants,  tokenTypeManager, twitchService, oAuthTokenProvider, oAuthTokenRefresher));
    }
}
