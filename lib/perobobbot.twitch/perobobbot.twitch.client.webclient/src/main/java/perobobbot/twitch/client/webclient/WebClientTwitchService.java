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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

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
    public @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions() {
        return webClientFactory.get("/eventsub/subscriptions")
                               .retrieve()
                               .bodyToMono(TwitchSubscriptionData.class);
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

}
