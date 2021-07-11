package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class SubscriptionData {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Conditions conditions;

    public SubscriptionData(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull Conditions conditions) {
        this.platform = platform;
        this.subscriptionType = subscriptionType;
        this.conditions = conditions;
    }

    public SubscriptionData(@NonNull Platform platform, @NonNull IdentifiedEnum subscriptionType, @NonNull Conditions conditions) {
        this(platform,subscriptionType.getIdentification(),conditions);
    }
}
