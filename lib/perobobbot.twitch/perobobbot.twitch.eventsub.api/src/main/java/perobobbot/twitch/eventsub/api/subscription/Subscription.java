package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.twitch.eventsub.api.ConditionId;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

import java.util.Map;

public interface Subscription {

    @NonNull SubscriptionType getType();

    @NonNull ImmutableMap<CriteriaType, String> getCondition();

    default @NonNull String getVersion() {
        return getType().getVersion();
    }

    default @NonNull ImmutableMap<String,String> cConditionMap() {
        return getCondition().entrySet().stream()
                             .collect(ImmutableMap.toImmutableMap(e -> e.getKey().getIdentification(), Map.Entry::getValue));
    }


}
