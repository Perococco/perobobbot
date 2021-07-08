package perobobbot.data.com;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.Map;
import java.util.UUID;

public record SubscriptionView(@NonNull UUID id,
                               @NonNull Platform platform,
                               @NonNull String subscriptionType,
                               @NonNull ImmutableMap<String,String> conditionMap,
                               @NonNull String subscriptionId) implements SubscriptionIdentity{


}
