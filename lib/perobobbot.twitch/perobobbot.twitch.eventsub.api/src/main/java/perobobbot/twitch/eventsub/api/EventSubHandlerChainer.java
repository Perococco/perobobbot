package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.lang.chain.ChainExecutor;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class EventSubHandlerChainer implements EventSubHandler {

    private final @NonNull ChainExecutor<TwitchRequestContent<EventSubRequest>, Nil> handleEventSubRequest;
    private final @NonNull ChainExecutor<ObjectWithLogin<UUID>, Mono<Nil>> handleDeleteSubscription;
    private final @NonNull ChainExecutor<ObjectWithLogin<Subscription>, Mono<UserSubscriptionView>> handleCreateSubscription;

    @Override
    public void handleEventSubRequest(@NonNull TwitchRequestContent<EventSubRequest> request) {
        handleEventSubRequest.call(request);
    }

    @Override
    public @NonNull Mono<Nil> handleSubscriptionDeletion(@NonNull String login, @NonNull UUID subscriptionId) {
        return handleDeleteSubscription.call(ObjectWithLogin.create(login, subscriptionId));
    }

    @Override
    public @NonNull Mono<UserSubscriptionView> handleCreateSubscription(@NonNull String login, @NonNull Subscription subscription) {
        return handleCreateSubscription.call(ObjectWithLogin.create(login, subscription));
    }

}
