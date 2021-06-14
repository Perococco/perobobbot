package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class SubscriptionEndEvent implements EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
}
