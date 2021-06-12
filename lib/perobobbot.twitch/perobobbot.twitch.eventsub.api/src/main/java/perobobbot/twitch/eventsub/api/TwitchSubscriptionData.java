package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class TwitchSubscriptionData {

    int total;
    @NonNull TwitchSubscription[] data;
    @JsonAlias("total_cost")
    int totalCost;
    @JsonAlias("max_total_cost")
    int maxTotalCost;
    @NonNull Object pagination;
}
