package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class StreamOfflineEvent implements EventSubEvent {
    @NonNull UserInfo broadcaster;
}
