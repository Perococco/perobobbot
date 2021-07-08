package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Conditions;
import perobobbot.lang.Platform;

@Value
public class SubscriptionData {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Conditions conditions;

}
