package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class TwitchSubscriptionData {

    int total;
    @NonNull TwitchSubscription[] data;
    @JsonAlias("total_cost")
    int totalCost;
    @JsonAlias("max_total_cost")
    int maxTotalCost;
    @NonNull Map<String,Object> pagination = new HashMap<>();
}
