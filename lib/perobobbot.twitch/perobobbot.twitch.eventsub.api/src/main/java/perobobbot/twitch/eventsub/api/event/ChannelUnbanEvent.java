package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelUnbanEvent implements EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo moderator;
}
