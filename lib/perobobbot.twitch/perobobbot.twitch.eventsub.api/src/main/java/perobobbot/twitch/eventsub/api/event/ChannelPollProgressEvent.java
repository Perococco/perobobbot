package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class ChannelPollProgressEvent implements EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<PollChoices> choices;
    @JsonAlias("bits_voting")
    @NonNull Voting bitsVoting;
    @JsonAlias("channel_points_voting")
    @NonNull Voting channelPointsVoting;
    @JsonAlias("started_at")
    @NonNull Instant startedAt;
    @JsonAlias("ends_at")
    @NonNull Instant endsAt;
}
