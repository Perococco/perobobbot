package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.subscription.Subscription;

public record ObjectWithLogin<T>(@NonNull String login, @NonNull T value) {

    public static <T> @NonNull ObjectWithLogin<T> create(@NonNull String login, @NonNull T value) {
        return new ObjectWithLogin<>(login,value);
    }

}
