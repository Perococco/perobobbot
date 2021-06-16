package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.chain.ChainExecutor;
import perobobbot.twitch.eventsub.api.subscription.Subscription;

@RequiredArgsConstructor
public class EventSubHandlerChainer implements EventSubHandler {

    private final @NonNull ChainExecutor<TwitchRequestContent<EventSubRequest>> handleEventSubRequest;
    private final @NonNull ChainExecutor<SubscriptionWithLogin> handleDeleteSubscription;
    private final @NonNull ChainExecutor<SubscriptionWithLogin> handleCreateSubscription;

    @Override
    public void handleEventSubRequest(@NonNull TwitchRequestContent<EventSubRequest> request) {
        handleEventSubRequest.call(request);
    }

    @Override
    public void handleDeleteSubscription(@NonNull String login, @NonNull Subscription subscription) {
        handleDeleteSubscription.call(new SubscriptionWithLogin(login,subscription));
    }

    @Override
    public void handleCreateSubscription(@NonNull String login, @NonNull Subscription subscription) {
        handleCreateSubscription.call(new SubscriptionWithLogin(login,subscription));
    }

}
