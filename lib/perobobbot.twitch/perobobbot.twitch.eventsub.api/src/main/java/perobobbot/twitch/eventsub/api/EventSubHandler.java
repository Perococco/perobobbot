package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.subscription.Subscription;

public interface EventSubHandler {

    void handleEventSubRequest(@NonNull TwitchRequestContent<EventSubRequest> request);

    void handleDeleteSubscription(@NonNull String login, @NonNull Subscription subscription);

    void handleCreateSubscription(@NonNull String login, @NonNull Subscription subscription);
}
