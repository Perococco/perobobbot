package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.*;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class SingleConditionSubscription implements Subscription {

    public static SubscriptionFactory forSingleCondition(@NonNull CriteriaType criteriaType, @NonNull Function1<? super String, ? extends Subscription>  constructor) {
        return condition -> constructor.apply(new ConditionHelper(condition).get(criteriaType));
    }

    @Getter
    private final @NonNull SubscriptionType type;

    private final @NonNull CriteriaType criteriaType;

    @Override
    public @NonNull ImmutableMap<CriteriaType, String> getCondition() {
        return ImmutableMap.of(criteriaType,getConditionValue());
    }

    protected abstract String getConditionValue();
}
