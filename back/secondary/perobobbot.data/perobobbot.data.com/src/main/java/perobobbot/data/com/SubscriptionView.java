package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public record SubscriptionView(@NonNull UUID id,
                               @NonNull Platform platform,
                               @NonNull String subscriptionType,
                               @NonNull String conditionId,
                               @NonNull String subscriptionId) implements SubscriptionIdentity{


}
