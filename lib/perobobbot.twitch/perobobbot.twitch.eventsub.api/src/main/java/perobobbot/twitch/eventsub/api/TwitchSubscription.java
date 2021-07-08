package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.lang.MapTool;
import perobobbot.lang.Platform;
import perobobbot.twitch.eventsub.api.deser.ConditionSerializer;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

import java.time.Instant;
import java.util.Map;

@Value
public class TwitchSubscription implements SubscriptionIdentity {

    @NonNull String id;
    @NonNull SubscriptionType type;
    @NonNull String version;
    @NonNull SubscriptionStatus status;
    int cost;
    @JsonSerialize(using = ConditionSerializer.class)
    @NonNull ImmutableMap<CriteriaType, String> condition;
    @NonNull Instant createdAt;
    @NonNull Transport transport;

    @JsonIgnore
    public @NonNull Class<? extends EventSubEvent> getEventType() {
        return type.getEventType();
    }

    @JsonIgnore
    public boolean isValid() {
        return status == SubscriptionStatus.ENABLED;
    }

    @JsonIgnore
    public boolean isFailure() {
        return status.isFailure();
    }

    @Override
    public @NonNull Platform platform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull String subscriptionType() {
        return type.getIdentification();
    }

    @Override
    public @NonNull ImmutableMap<String, String> conditionMap() {
        return condition.entrySet().stream().collect(ImmutableMap.toImmutableMap(c -> c.getKey().getIdentification(), Map.Entry::getValue));
    }

    @Override
    public @NonNull String subscriptionId() {
        return id;
    }
}
