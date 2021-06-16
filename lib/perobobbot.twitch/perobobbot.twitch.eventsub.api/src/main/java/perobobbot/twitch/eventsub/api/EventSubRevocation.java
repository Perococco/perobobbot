package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class EventSubRevocation implements EventSubRequest {

    @NonNull TwitchSubscription subscription;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
