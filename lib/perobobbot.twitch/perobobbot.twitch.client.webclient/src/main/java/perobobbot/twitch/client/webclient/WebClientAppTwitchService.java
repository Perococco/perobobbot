package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.Nil;
import perobobbot.lang.Todo;
import perobobbot.twitch.client.api.GameSearchParameter;
import perobobbot.twitch.client.api.TwitchService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class WebClientAppTwitchService implements TwitchService {

    private final @NonNull WebClientFactory webClientFactory;

    @Override
    public @NonNull Mono<String> getGames(@NonNull GameSearchParameter parameter) {
        final var queryParams = parameter.createQueryParameters();

        System.out.println("Thread in WebClientAppTwitchService " + Thread.currentThread().getName());

        return webClientFactory.create()
                               .get()
                               .uri("/games", uri -> {
                                   queryParams.forEach(uri::queryParam);
                                   return uri.build();
                               })
                               .retrieve()
                               .bodyToMono(String.class)
                               .subscribeOn(Schedulers.newSingle("Toto"));
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
