package perobobbot.twitch.eventsub.api.condition;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserAuthorizationRevoke extends SingleConditionSubscription {

    @NonNull String clientId;

    public UserAuthorizationRevoke(@NonNull String clientId) {
        super(SubscriptionType.USER_AUTHORIZATION_REVOKE,CriteriaType.CLIENT_ID);
        this.clientId = clientId;
    }

    @Override
    protected String getConditionValue() {
        return clientId;
    }
}
