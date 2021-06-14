package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class RaidEvent implements EventSubEvent {

    @NonNull UserInfo fromBroadcaster;
    @NonNull UserInfo toBroadcaster;
    int viewers;
}
