package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class ModeratorRemoveEvent implements BroadcasterProvider, EventSubEvent{

    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
}
