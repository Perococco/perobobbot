package perobobbot.twitch.client.webclient.eventsub;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Nil;
import perobobbot.oauth.ClientApiToken;
import perobobbot.oauth.OAuthWebClientFactory;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSubWithToken;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class WebClientTwitchServiceEventSub implements TwitchServiceEventSubWithToken {

    private final @NonNull OAuthWebClientFactory webClientFactory;

    @Override
    public @NonNull Mono<TwitchSubscriptionData> createEventSubSubscription(@NonNull ClientApiToken token, @NonNull TwitchSubscriptionRequest request) {
        return webClientFactory.create(token)
                               .post("/eventsub/subscriptions")
                               .body(Mono.just(request), TwitchSubscriptionRequest.class)
                               .retrieve()
                               .bodyToMono(TwitchSubscriptionData.class);
    }

    @Override
    public @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions(@NonNull ClientApiToken token) {
        return webClientFactory.create(token)
                               .get("/eventsub/subscriptions")
                               .retrieve()
                               .bodyToMono(TwitchSubscriptionData.class);
    }

    @Override
    public @NonNull Mono<Nil> deleteEventSubSubscription(@NonNull ClientApiToken token, @NonNull String id) {
        return webClientFactory.create(token)
                               .delete("/eventsub/subscriptions", Map.of("id", List.of(id)))
                               .retrieve()
                               .toBodilessEntity().map(r -> Nil.NIL);
    }

}
