package perobobbot.twitch.eventsub.api;

import lombok.NonNull;

public interface EventSubRequest {

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> {

        @NonNull T visit(@NonNull EventSubNotification notification);
        @NonNull T visit(@NonNull EventSubRevocation revocation);
        @NonNull T visit(@NonNull EventSubVerification verification);

    }

}
