package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class PredictionLockEvent implements PredicationEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<Outcome> outcomes;
    @NonNull Instant startedAt;

    @NonNull Instant lockedAt;

}
