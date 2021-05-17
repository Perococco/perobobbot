package perobbot.twitch.oauth.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import perobbot.twitch.oauth.TwitchOAuthController;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Instants;
import perobobbot.lang.Packages;
import perobobbot.lang.Platform;
import perobobbot.oauth.ClientProperties;
import perobobbot.oauth.OAuthSubscriptions;

@Configuration
@RequiredArgsConstructor
public class TwitchOAuthConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch API", TwitchOAuthConfiguration.class);
    }

    private final @NonNull Instants instants;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull ClientProperties clientProperties;

    @Bean
    public TwitchOAuthController oAuthController() {
        final var clientProperty = clientProperties.getClientProperty(Platform.TWITCH);
        return new TwitchOAuthController(oAuthSubscriptions(), WebClient.create(), instants, clientProperty);
    }

    @Bean(destroyMethod = "removeAll")
    public OAuthSubscriptions oAuthSubscriptions() {
        return new OAuthSubscriptions(instants, webHookManager);
    }

}
