package perobbot.twitch.oauth.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import perobbot.twitch.oauth.TwitchOAuthController;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.TokenService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Instants;
import perobobbot.lang.Packages;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthSubscriptions;

@Configuration
@RequiredArgsConstructor
public class TwitchOAuthConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch API", TwitchOAuthConfiguration.class);
    }

    private final @NonNull Instants instants;
    private final @NonNull WebHookManager webHookManager;

    @Bean
    public OAuthController.Factory twitchOAuthControllerFactory() {
        return new TwitchOAuthControllerFactory(oAuthSubscriptions(), instants);
    }

    @Bean(destroyMethod = "removeAll")
    public OAuthSubscriptions oAuthSubscriptions() {
        return new OAuthSubscriptions(instants, webHookManager);
    }

}
