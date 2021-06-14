package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

@Value
public class EventSubNotification implements EvenSubRequest {

    @NonNull TwitchSubscription subscription;
    @NonNull EventSubEvent event;

    @Override
    public void accept(@NonNull Visitor visitor) {
        visitor.visit(this);
    }
}
