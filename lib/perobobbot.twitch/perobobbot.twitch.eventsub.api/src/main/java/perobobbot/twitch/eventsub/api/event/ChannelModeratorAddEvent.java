package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelModeratorAddEvent implements EventSubEvent{

    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
}
