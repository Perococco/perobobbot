package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class RaidEvent implements BroadcasterProvider, EventSubEvent {

    @NonNull UserInfo fromBroadcaster;
    @NonNull UserInfo toBroadcaster;
    int viewers;

    @Override
    public @NonNull UserInfo getBroadcaster() {
        return toBroadcaster;
    }
}
