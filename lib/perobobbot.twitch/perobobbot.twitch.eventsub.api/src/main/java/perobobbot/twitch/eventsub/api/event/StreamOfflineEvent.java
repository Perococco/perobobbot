package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class StreamOfflineEvent implements EventSubEvent {
    @NonNull UserInfo broadcaster;
}
