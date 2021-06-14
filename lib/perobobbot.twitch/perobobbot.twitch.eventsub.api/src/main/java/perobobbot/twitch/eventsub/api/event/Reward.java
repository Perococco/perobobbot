package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class Reward {
    @NonNull String id;
    @NonNull String title;
    int cost;
    @NonNull String prompt;
}
