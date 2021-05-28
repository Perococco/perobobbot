package perobobbot.twitch.client.webclient.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.*;
import perobobbot.lang.Todo;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.webclient.WebClientAppTwitchService;
import reactor.core.publisher.Mono;

@Configuration
public class TwitchApiConfiguration {


    @Bean
    public TwitchService twitchService() {
        final var webClient = WebClient.builder().filter((request, next) -> {
            OAuthContextHolder.getContext().getHeaderValue().ifPresent(v -> request.headers().add(HttpHeaders.AUTHORIZATION, v));
            return next.exchange(request);
        }).build();

        final var webClientImplementation = new WebClientAppTwitchService(webClient);
        return webClientImplementation;
    }
}
