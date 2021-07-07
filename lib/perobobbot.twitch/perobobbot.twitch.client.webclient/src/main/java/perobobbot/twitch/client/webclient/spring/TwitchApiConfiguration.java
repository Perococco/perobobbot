package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.CastTool;
import perobobbot.lang.Packages;
import perobobbot.oauth.tools.ApiTokenHelperFactory;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceWithToken;
import perobobbot.twitch.client.webclient.ProxyTwitchService;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class TwitchApiConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch API", TwitchApiConfiguration.class);
    }

    private final @NonNull WebClientFactory webClientFactory;

    private final @NonNull ApiTokenHelperFactory apiTokenHelperFactory;

    @Bean
    public TwitchService twitchService() {
        return TwitchServiceHandler.createService(twitchServiceWithToken(),apiTokenHelperFactory);
    }

    @Bean
    public TwitchServiceWithToken twitchServiceWithToken() {
        final var builder = webClientFactory.mutate()
                                            .baseUrl("https://api.twitch.tv/helix")
                                            .addModifier(
                                                    b -> b.filter(ExchangeFilterFunction.ofResponseProcessor(
                                                            this::displayLimitHeaders)))
                                            .build();

        return new ProxyTwitchService(new TwitchOAuthWebClientFactory(builder));
    }


    private @NonNull Mono<ClientResponse> displayLimitHeaders(@NonNull ClientResponse response) {
        final var remaining = response.headers()
                                      .header("Ratelimit-Remaining")
                                      .stream()
                                      .map(CastTool::castToInt)
                                      .flatMap(Optional::stream)
                                      .mapToInt(Integer::intValue)
                                      .min();

        if (remaining.isPresent() && remaining.getAsInt() < 10) {
            LOG.warn("Only {} request remaining", remaining);
        }

        return Mono.just(response);
    }

}
