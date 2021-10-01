package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

@Value
public class StreamOnlineEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull StreamType type;
    @NonNull Instant startedAt;

}
