package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class EventSubRevocation implements EvenSubRequest {

    @NonNull TwitchSubscription subscription;

    @Override
    public void accept(@NonNull Visitor visitor) {
        visitor.visit(this);
    }
}
