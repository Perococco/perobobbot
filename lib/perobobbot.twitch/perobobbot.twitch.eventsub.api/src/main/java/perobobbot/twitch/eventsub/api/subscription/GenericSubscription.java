package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@RequiredArgsConstructor
@Getter
public class GenericSubscription implements Subscription {

    public static GenericSubscription from(@NonNull String subscriptionType, @NonNull ImmutableMap<String,String> conditions) {
        final Function1<String,CriteriaType> toCriteria = s -> IdentifiedEnumTools.getEnum(s,CriteriaType.class);
        final var type = IdentifiedEnumTools.getEnum(subscriptionType,SubscriptionType.class);
        final var cond = conditions.keySet().stream().collect(ImmutableMap.toImmutableMap(toCriteria, conditions::get));

        return new GenericSubscription(type,cond);
    }

    private final @NonNull SubscriptionType type;
    private final @NonNull ImmutableMap<CriteriaType, String> condition;
}
