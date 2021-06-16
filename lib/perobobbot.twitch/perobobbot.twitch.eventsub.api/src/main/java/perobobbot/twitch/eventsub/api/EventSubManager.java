package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.subscription.Subscription;

public interface EventSubManager {
    
    void deleteSubscription(@NonNull String login, @NonNull Subscription subscription);

    void createSubscription(@NonNull String login, @NonNull Subscription subscription);
}
