package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.lang.Conditions;
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
    @JsonIgnore
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    @JsonIgnore
    public @NonNull String getSubscriptionType() {
        return type.getIdentification();
    }

    @Override
    @JsonIgnore
    public @NonNull Conditions getConditions() {
        final var builder = ImmutableMap.<String,String>builder();
        condition.forEach((k,v) -> builder.put(k.getIdentification(),v));
        return new Conditions(builder.build());
    }

    @Override
    @JsonIgnore
    public @NonNull String getSubscriptionId() {
        return id;
    }

    @Override
    @JsonIgnore
    public @NonNull String getCallbackUrl() {
        return transport.getCallback();
    }
}
