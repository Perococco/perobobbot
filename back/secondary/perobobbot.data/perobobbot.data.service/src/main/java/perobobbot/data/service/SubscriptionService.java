package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface SubscriptionService {

    @NonNull Stream<UserSubscriptionView> listAllSubscriptions(@NonNull String login);

    @NonNull Stream<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<SubscriptionView> findSubscription(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull String conditionId);

    @NonNull UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login);

    @NonNull SubscriptionView createSubscription(@NonNull Platform platform, @NonNull String subscriptionTwitchId, @NonNull String subscriptionType, @NonNull String conditionId);

    /**
     * @param id the id of the subscription to clean
     * @return an optional containing a view of the subscription if it has been deleted by the clean up, an empty optional otherwise
     */
    @NonNull Optional<SubscriptionView> cleanSubscription(@NonNull UUID id);

    void deleteUserSubscription(@NonNull UUID id, @NonNull String login);
}
