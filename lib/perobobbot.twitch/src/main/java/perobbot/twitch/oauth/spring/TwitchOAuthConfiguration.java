package perobbot.twitch.oauth.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import perobbot.twitch.oauth.TwitchOAuthController;
import perobobbot.http.WebHookObservable;
import perobobbot.lang.Instants;
import perobobbot.lang.Packages;
import perobobbot.oauth.OAuthSubscriptions;

@Configuration
@RequiredArgsConstructor
public class TwitchOAuthConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch API", TwitchOAuthConfiguration.class);
    }

    private final @NonNull Instants instants;
    private final @NonNull WebHookObservable webHookObservable;

    @Bean
    public TwitchOAuthController oAuthController() {
        return new TwitchOAuthController(oAuthSubscriptions(),new RestTemplate());
    }

    @Bean(destroyMethod = "removeAll")
    public OAuthSubscriptions oAuthSubscriptions() {
        return new OAuthSubscriptions(instants,webHookObservable);
    }

}
