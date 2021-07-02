package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;

@Value
@NonFinal
public abstract class PollEvent implements EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<PollChoices> choices;
    @NonNull Voting bitsVoting;
    @NonNull Voting channelPointsVoting;
    @NonNull Instant startedAt;

}
