package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

@Value
public class HypeTrainEndEvent implements EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    int level;
    int total;
    @NonNull ImmutableList<Contribution> topContributions;
    @NonNull Instant startedAt;
    @NonNull Instant endedAt;
    @NonNull Instant cooldownEndsAt;

}
