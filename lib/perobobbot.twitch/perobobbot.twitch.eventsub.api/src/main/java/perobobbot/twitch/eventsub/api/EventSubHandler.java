package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EventSubHandler {

    void handleEventSubRequest(@NonNull TwitchRequestContent<EventSubRequest> request);

    @NonNull Mono<Nil> handleSubscriptionDeletion(@NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> handleCreateSubscription(@NonNull String login, @NonNull Subscription subscription);
}
