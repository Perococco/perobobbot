package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class ModeratorAddEvent implements BroadcasterProvider, EventSubEvent {

    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
}
