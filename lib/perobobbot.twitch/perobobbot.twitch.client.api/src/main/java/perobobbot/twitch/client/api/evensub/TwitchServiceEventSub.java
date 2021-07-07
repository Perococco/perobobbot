package perobobbot.twitch.client.api.evensub;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.oauth.ClientOAuth;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Mono;

public interface TwitchServiceEventSub {

    @ClientOAuth
    @NonNull Mono<TwitchSubscriptionData> createEventSubSubscription(@NonNull TwitchSubscriptionRequest request);

    @ClientOAuth
    @NonNull Mono<Nil> deleteEventSubSubscription(@NonNull String id);

    @ClientOAuth
    @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions();

}
