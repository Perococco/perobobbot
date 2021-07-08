package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Service providing access to the persisted subscription
 */
public interface SubscriptionService {

    /**
     * @param login the login of the user
     * @return all the subscriptions made by the user with the provided login
     */
    @NonNull Stream<UserSubscriptionView> listAllSubscriptions(@NonNull String login);

    /**
     * @param login the login of the user
     * @param platform a platform
     * @return all the subscriptions made by the user with the provided login for the provided platform
     */
    @NonNull Stream<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<SubscriptionView> findSubscription(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull String conditionId);

    @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform);

    @NonNull UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login);

    @NonNull SubscriptionView createSubscription(@NonNull Platform platform, @NonNull String subscriptionTwitchId, @NonNull String subscriptionType, @NonNull String conditionId);

    /**
     * @param subscriptionDbId the id in the database
     * @param subscriptionId the id on the platform
     */
    void updateSubscriptionId(@NonNull UUID subscriptionDbId, @NonNull String subscriptionId);
    /**
     * @param id the id of the subscription to clean
     * @return an optional containing a view of the subscription if it has been deleted by the clean up, an empty optional otherwise
     */
    @NonNull Optional<SubscriptionView> cleanSubscription(@NonNull UUID id);

    void deleteUserSubscription(@NonNull UUID id, @NonNull String login);

}
