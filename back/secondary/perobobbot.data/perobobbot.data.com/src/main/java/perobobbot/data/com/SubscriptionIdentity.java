package perobobbot.data.com;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.Platform;

public interface SubscriptionIdentity {

    @NonNull Platform platform();
    @NonNull String subscriptionType();
    @NonNull ImmutableMap<String,String> conditionMap();
    @NonNull String subscriptionId();
}
