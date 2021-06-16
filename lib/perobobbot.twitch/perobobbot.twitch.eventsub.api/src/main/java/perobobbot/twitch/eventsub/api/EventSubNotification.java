package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

@Value
public class EventSubNotification implements EventSubRequest {

    @NonNull TwitchSubscription subscription;
    @NonNull EventSubEvent event;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
