package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.deser.ConditionSerializer;

@Value
public class TwitchSubscription {

    @NonNull String id;
    @NonNull SubscriptionStatus status;
    @NonNull SubscriptionType type;
    @NonNull String version;
    @JsonSerialize(using = ConditionSerializer.class)
    @NonNull ImmutableMap<CriteriaType,String> condition;
    @JsonAlias("created_at")
    @NonNull String createdAt;
    @NonNull Transport transport;
    int cost;

}
