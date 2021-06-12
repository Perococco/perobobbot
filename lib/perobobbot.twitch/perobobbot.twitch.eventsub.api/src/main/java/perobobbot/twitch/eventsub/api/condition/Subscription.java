package perobobbot.twitch.eventsub.api.condition;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

public interface Subscription {

    @NonNull SubscriptionType getType();

    default @NonNull String getVersion() {
        return getType().getVersion();
    }

    @NonNull ImmutableMap<CriteriaType,String> getCondition();
}
