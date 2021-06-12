package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.twitch.eventsub.api.condition.Subscription;
import reactor.core.publisher.Mono;

public interface EventSubManager {

    @NonNull Mono<TwitchSubscription> subscribe(@NonNull Subscription subscription);

    @NonNull Mono<Nil> deleteSubscription(@NonNull String id);

    @NonNull Mono<TwitchSubscriptionData> listSubscriptions();

}
