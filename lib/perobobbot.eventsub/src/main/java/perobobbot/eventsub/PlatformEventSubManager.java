package perobobbot.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

public interface PlatformEventSubManager {

    /**
     * @return the platform this manager applies to
     */
    @NonNull Platform getPlatform();

    /**
     * @return the type of subscription the platform provides
     */
    @NonNull ImmutableSet<String> getSubscriptionTypes();

    /**
     * @param login          the login of the user
     * @param subscriptionId the id of the subscription the user is attached to
     * @return
     */
    @NonNull Mono<Nil> deleteUserSubscription(@NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login,
                                                               @NonNull String subscriptionType,
                                                               @NonNull ImmutableMap<String, String> conditions);


    @NonNull Mono<Platform> cleanFailedSubscription();

    @NonNull Mono<Nil> revokeSubscription(@NonNull String subscriptionId);


    @NonNull Mono<? extends SubscriptionIdentity> createSubscription(@NonNull String subscriptionType,
                                                       @NonNull ImmutableMap<String, String> conditions);


    @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllValidSubscriptions();

}
