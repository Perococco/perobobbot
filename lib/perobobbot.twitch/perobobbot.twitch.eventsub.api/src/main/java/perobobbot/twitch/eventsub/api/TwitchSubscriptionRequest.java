package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.deser.ConditionSerializer;

@Value
@Builder
public class TwitchSubscriptionRequest {

    @NonNull SubscriptionType type;
    @NonNull String version;
    @JsonSerialize(using = ConditionSerializer.class)
    @NonNull ImmutableMap<CriteriaType,String> condition;
    @NonNull TransportRequest transport;

}
