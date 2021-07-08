package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class SubscriptionData {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Conditions conditions;

}
