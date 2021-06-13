package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.Tier;

@Value
public class ChannelSubscribeEvent implements EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Tier tier;
    boolean gift;
}
