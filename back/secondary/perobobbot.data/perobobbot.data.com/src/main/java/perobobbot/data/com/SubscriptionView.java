package perobobbot.data.com;

import lombok.NonNull;

import java.util.UUID;

public record SubscriptionView(@NonNull UUID id,
                               @NonNull String subcriptionType,
                               @NonNull String conditionId,
                               @NonNull String subscriptionId) {


}
