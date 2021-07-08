package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Conditions;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@RequiredArgsConstructor
@Getter
public class GenericSubscription implements Subscription {

    public static GenericSubscription from(@NonNull String subscriptionType, @NonNull Conditions conditions) {

        final var type = IdentifiedEnumTools.getEnum(subscriptionType,SubscriptionType.class);
        final var cond = conditions.toMap(IdentifiedEnumTools.mapper(CriteriaType.class));

        return new GenericSubscription(type,cond);
    }

    private final @NonNull SubscriptionType type;
    private final @NonNull ImmutableMap<CriteriaType, String> condition;
}
