package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.subscription.Subscription;

public record SubscriptionWithLogin(@NonNull String login, @NonNull Subscription subscription) {
}
