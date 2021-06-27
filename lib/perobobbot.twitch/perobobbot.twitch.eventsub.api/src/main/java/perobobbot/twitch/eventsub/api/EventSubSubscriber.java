package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Mono;

public interface EventSubSubscriber {

    @NonNull Mono<TwitchSubscription> subscribeToEventSub(@NonNull Subscription subscription);

}
