package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import perobobbot.lang.Packages;
import perobobbot.oauth.tools.OAuthWebClientFactory;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.webclient.WebClientAppTwitchService;

@Configuration
public class TwitchApiConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch API", TwitchApiConfiguration.class);
    }


    @Bean
    public TwitchService twitchService() {
        final var webClient = WebClient.builder()
                                       .baseUrl("https://api.twitch.tv/helix")
                                       .build();

        return new WebClientAppTwitchService(new OAuthWebClientFactory(webClient));
    }


}
