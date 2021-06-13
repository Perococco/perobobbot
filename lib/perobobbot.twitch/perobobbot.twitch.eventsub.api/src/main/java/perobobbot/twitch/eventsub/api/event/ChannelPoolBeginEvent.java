package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelPoolBeginEvent implements EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<PollChoices> choices;
    @JsonAlias("bits_voting")
    @NonNull Voting bitsVoting;
    @JsonAlias("channel_points_voting")
    @NonNull Voting channelPointsVoting;
    @JsonAlias("started_at")
    @NonNull String startedAt;
    @JsonAlias("ends_at")
    @NonNull String endsAt;
}
