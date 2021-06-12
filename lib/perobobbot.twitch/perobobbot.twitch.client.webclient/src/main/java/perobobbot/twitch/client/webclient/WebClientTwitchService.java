package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.Nil;
import perobobbot.lang.Todo;
import perobobbot.twitch.client.api.Game;
import perobobbot.twitch.client.api.GameSearchParameter;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class WebClientTwitchService implements TwitchService {

    private final @NonNull WebClientFactory webClientFactory;

    @Override
    public @NonNull Flux<Game> getGames(@NonNull GameSearchParameter parameter) {
        final var queryParams = parameter.createQueryParameters();

        return webClientFactory.get("/games", queryParams)
                               .retrieve()
                               .bodyToMono(GetGamesResponse.class)
                               .flatMapIterable(g -> Arrays.asList(g.getData()));
    }

    @Override
    public @NonNull Mono<TwitchSubscriptionData> subscriptToEventSub(@NonNull TwitchSubscriptionRequest request) {
        return webClientFactory.post("/eventsub/subscriptions")
                               .body(Mono.just(request), TwitchSubscriptionRequest.class)
                               .retrieve()
                               .bodyToMono(TwitchSubscriptionData.class);
    }

    @Override
    public @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions() {
        return webClientFactory.get("/eventsub/subscriptions")
                               .retrieve()
                               .bodyToMono(TwitchSubscriptionData.class);
    }

    @Override
    public @NonNull Mono<Nil> deleteEventSubSubscription(@NonNull String id) {
        return webClientFactory.delete("/eventsub/subscriptions", Map.of("id", List.of(id)))
                               .retrieve()
                               .toBodilessEntity().map(r -> Nil.NIL);
    }

    @Override
    public Flux<Nil> GetFollowedStreams(@NonNull String userId) {
        return Todo.TODO();
    }

    @Override
    public Flux<Nil> getStreamTags() {
        return Todo.TODO();
    }

}
