package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import perobobbot.twitch.eventsub.api.PollStatus;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PollEndEvent extends PollEvent {

    @NonNull PollStatus status;
    @NonNull Instant endedAt;

    @java.beans.ConstructorProperties({"id", "broadcaster", "title", "choices", "bitsVoting", "channelPointsVoting", "startedAt","status","endedAt"})
    public PollEndEvent(@NonNull String id,
                        @NonNull UserInfo broadcaster,
                        @NonNull String title,
                        @NonNull ImmutableList<PollChoices> choices,
                        @NonNull Voting bitsVoting,
                        @NonNull Voting channelPointsVoting,
                        @NonNull Instant startedAt,
                        @NonNull PollStatus status,
                        @NonNull Instant endedAt) {
        super(id, broadcaster, title, choices, bitsVoting, channelPointsVoting, startedAt);
        this.endedAt = endedAt;
        this.status = status;
    }

}
