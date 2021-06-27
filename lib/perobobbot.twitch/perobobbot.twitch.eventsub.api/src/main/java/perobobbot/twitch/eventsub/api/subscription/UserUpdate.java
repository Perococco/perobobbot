package perobobbot.twitch.eventsub.api.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserUpdate extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.USER_ID, UserUpdate::new);

    @NonNull String userId;

    public UserUpdate(@NonNull String userId) {
        super(SubscriptionType.USER_UPDATE,CriteriaType.USER_ID);
        this.userId = userId;
    }

    @Override
    protected String getConditionValue() {
        return userId;
    }
}
