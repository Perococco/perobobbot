package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Value;

@Value
public class Voting {

    @JsonAlias("is_enabled")
    boolean enabled;

    @JsonAlias("amount_per_vote")
    int amountPerVote;
}
