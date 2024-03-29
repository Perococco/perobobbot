package perobobbot.twitch.oauth.impl.spring;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Packages;
import perobobbot.oauth.OAuthController;
import perobobbot.twitch.oauth.impl.TwitchOAuthController;

@Configuration
public class TwitchOAuthConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch OAuth", TwitchOAuthConfiguration.class);
    }

    @Bean
    public OAuthController.Factory twitchOAuthController() {
        return (oAuthSubscriptions,instants) -> new TwitchOAuthController(oAuthSubscriptions, WebClient.create(),instants);
    }

}
