package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class ModeratorRemoveEvent implements EventSubEvent{

    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
}
