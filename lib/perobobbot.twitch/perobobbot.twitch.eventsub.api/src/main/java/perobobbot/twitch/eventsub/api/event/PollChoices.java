package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class PollChoices {
    @NonNull String id;
    @NonNull String title;
    @JsonAlias("bits_votes")
    int bitsVotes;
    @JsonAlias("channel_points_votes")
    int channelPointVotes;
    int votes;
}
