package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;

import java.util.Optional;
import java.util.UUID;

/**
 * Service providing access to the persisted subscription
 */
public interface SubscriptionService {

    static int VERSION = 1;

    /**
     * @param login the login of the user
     * @return all the subscriptions made by the user with the provided login
     */
    @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptions(@NonNull String login);

    /**
     * @param login    the login of the user
     * @param platform a platform
     * @return all the subscriptions made by the user with the provided login for the provided platform
     */
    @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform);

    @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform);


    @NonNull UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login);

    /**
     * @param subscriptionId the id of the subscription
     * @param login          the login of the user
     */
    void removeUserFromSubscription(@NonNull UUID subscriptionId, @NonNull String login);


    /**
     * @param subscriptionData the data that defines the subscription
     * @return an optional containing the subscription if it exists, an empty optional otherwise
     */
    @NonNull Optional<SubscriptionView> findSubscription(@NonNull SubscriptionData subscriptionData);


    /**
     * @param subscriptionData the data that defines the subscription
     */
    @NonNull SubscriptionView getOrCreateSubscription(@NonNull SubscriptionData subscriptionData);


    /**
     * @param subscriptionDbId the id in the database
     * @param subscriptionIdentity  the answer from the platform to the subscription request
     */
    void updateSubscriptionWithPlatformAnswer(@NonNull UUID subscriptionDbId, @NonNull SubscriptionIdentity subscriptionIdentity);

    /**
     * @param id the id of the subscription to clean
     * @return an optional containing a view of the subscription if it has been deleted by the clean up, an empty optional otherwise
     */
    @NonNull Optional<SubscriptionView> cleanSubscription(@NonNull UUID id);


    void clearCallbackUrlIfDoesNotStartWith(String callbackHost);
}
