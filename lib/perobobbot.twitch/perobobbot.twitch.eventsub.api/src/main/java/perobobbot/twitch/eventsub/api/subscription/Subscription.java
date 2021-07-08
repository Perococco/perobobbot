package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.Conditions;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

public interface Subscription {

    @NonNull SubscriptionType getType();

    @NonNull ImmutableMap<CriteriaType, String> getCondition();

    default @NonNull String getVersion() {
        return getType().getVersion();
    }

    default @NonNull Conditions conditionMap() {
        return Conditions.withIdentifiedEnumAsKey(getCondition());
    }


}
