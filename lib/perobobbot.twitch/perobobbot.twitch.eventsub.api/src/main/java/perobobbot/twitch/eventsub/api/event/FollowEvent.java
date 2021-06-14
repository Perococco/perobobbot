package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class FollowEvent implements EventSubEvent {

    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Instant followedAt;
}
