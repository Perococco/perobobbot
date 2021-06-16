package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class EventSubVerification  implements EventSubRequest {

    @NonNull String challenge;
    @NonNull TwitchSubscription subscription;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
