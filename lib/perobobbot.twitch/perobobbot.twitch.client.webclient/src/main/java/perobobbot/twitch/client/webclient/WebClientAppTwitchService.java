package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Nil;
import perobobbot.lang.Todo;
import perobobbot.twitch.client.api.GameSearchParameter;
import perobobbot.twitch.client.api.TwitchService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientAppTwitchService implements TwitchService {

    private final @NonNull WebClient webClient;


    @Override
    public @NonNull String getGames(@NonNull GameSearchParameter parameter) {
        final var queryParams = parameter.createQueryParameters();
        return webClient.get()
                        .uri("/games", uri -> {
                            queryParams.forEach((k, v) -> uri.queryParam(k, v));
                            return uri.build();
                        })
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }

    @Override
    public Flux<Nil> GetFollowedStreams(@NonNull String userId) {
        return Todo.TODO();
    }

    @Override
    public Flux<Nil> getStreamTags() {
        return Todo.TODO();
    }

    @Override
    public Mono<Nil> createEventSubSubscription() {
        return Todo.TODO();
    }

    @Override
    public Mono<Nil> deleteEventSubSubscription() {
        return Todo.TODO();
    }

    @Override
    public Mono<Nil> getEventSubSubscriptions() {
        return Todo.TODO();
    }
}
