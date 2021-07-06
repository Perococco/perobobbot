package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.lang.Platform;
import perobobbot.twitch.eventsub.api.deser.ConditionSerializer;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

import java.time.Instant;

@Value
public class TwitchSubscription implements SubscriptionIdentity {

    @NonNull String id;
    @NonNull SubscriptionType type;
    @NonNull String version;
    @NonNull SubscriptionStatus status;
    int cost;
    @JsonSerialize(using = ConditionSerializer.class)
    @NonNull ImmutableMap<CriteriaType,String> condition;
    @NonNull Instant createdAt;
    @NonNull Transport transport;

    public @NonNull Class<? extends EventSubEvent> getEventType() {
        return type.getEventType();
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
    public @NonNull String conditionId() {
        return new ConditionId(condition).toString();
    }

    @Override
    public @NonNull String subscriptionId() {
        return id;
    }
}
