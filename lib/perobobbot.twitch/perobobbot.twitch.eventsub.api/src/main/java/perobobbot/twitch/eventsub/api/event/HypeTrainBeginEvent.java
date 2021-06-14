package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class HypeTrainBeginEvent implements HypeTrainEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    int total;

    int progress;
    int goal;
    @NonNull ImmutableList<Contribution> topContributions;
    @NonNull Contribution lastContribution;
    @NonNull Instant startedAt;
    @NonNull Instant expiresAt;

}
