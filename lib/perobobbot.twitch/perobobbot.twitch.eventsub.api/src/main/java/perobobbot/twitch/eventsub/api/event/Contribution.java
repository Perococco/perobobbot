package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class Contribution {
    @NonNull UserInfo user;
    @NonNull ContributionType type;
    int total;
}
