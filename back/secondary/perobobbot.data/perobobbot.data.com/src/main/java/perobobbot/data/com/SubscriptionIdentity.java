package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.Platform;

public interface SubscriptionIdentity {

    @NonNull Platform platform();
    @NonNull String subscriptionType();
    @NonNull String conditionId();
    @NonNull String subscriptionId();
}
