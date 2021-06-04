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

import java.util.Objects;

@RequiredArgsConstructor
public class WebClientAppTwitchService implements TwitchService {

    private final @NonNull WebClient webClient;


    @Override
    public @NonNull String getGames(@NonNull GameSearchParameter parameter) {
        final var queryParams = parameter.createQueryParameters();

        final var result = webClient.get()
                                    .uri("/games", uri -> {
                                        queryParams.forEach(uri::queryParam);
                                        return uri.build();
                                    })
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block();

        return Objects.requireNonNull(result);
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
