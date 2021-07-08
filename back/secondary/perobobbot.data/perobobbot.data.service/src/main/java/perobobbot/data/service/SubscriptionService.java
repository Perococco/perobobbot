package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
    @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptions(@NonNull String login);

    /**
     * @param login the login of the user
     * @param platform a platform
     * @return all the subscriptions made by the user with the provided login for the provided platform
     */
    @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform);

    @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform);

    @NonNull Optional<SubscriptionView> findSubscription(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull ImmutableMap<String,String> conditions);


    @NonNull UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login);

    /**
     * @param subscriptionId the id of the subscription
     * @param login the login of the user
     */
    void removeUserFromSubscription(@NonNull UUID subscriptionId, @NonNull String login);

    @NonNull SubscriptionView createSubscription(@NonNull Platform platform, @NonNull String subscriptionTwitchId, @NonNull String subscriptionType, @NonNull ImmutableMap<String,String> conditions);

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


}
