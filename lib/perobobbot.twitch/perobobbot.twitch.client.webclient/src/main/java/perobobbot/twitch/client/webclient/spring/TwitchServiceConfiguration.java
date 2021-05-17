package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.Instants;
import perobobbot.lang.Packages;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthTokenRefresher;
import perobobbot.twitch.client.api.TwitchServiceFactory;

@Configuration
@RequiredArgsConstructor
public class TwitchServiceConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch Service", TwitchServiceConfiguration.class);
    }

    private final @NonNull TokenTypeManager tokenTypeManager;
    private final @NonNull Instants instants;
    private final @NonNull OAuthController oAuthController;

    @Bean
    public @NonNull TwitchServiceFactory twitchServiceFactory() {
        return new WebFluxTwitchServiceFactory(instants, tokenTypeManager,oAuthController);
    }
}
