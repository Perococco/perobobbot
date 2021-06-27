package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.subscription.Subscription;

public record ObjectWithLogin<T>(@NonNull String login, @NonNull T value) {

}
