package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.PollStatus;

@Value
public class ChannelPoolEndEvent implements EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<PollChoices> choices;
    @JsonAlias("bits_voting")
    @NonNull Voting bitsVoting;
    @JsonAlias("channel_points_voting")
    @NonNull Voting channelPointsVoting;
    @NonNull PollStatus status;
    @JsonAlias("started_at")
    @NonNull String startedAt;
    @JsonAlias("ended_at")
    @NonNull String endedAt;
}
