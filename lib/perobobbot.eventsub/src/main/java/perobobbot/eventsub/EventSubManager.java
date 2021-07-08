package perobobbot.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

/**
 * Methods to get information about event subscription on the platform
 */
public interface EventSubManager {

    boolean isPlatformAvailable(@NonNull Platform platform);

    @NonNull Set<String> getSubscriptionTypes(@NonNull Platform platform);


    //INFO: methods below does not modify bdd with chain executor

    @NonNull Flux<Platform> cleanFailedSubscription();

    @NonNull Mono<? extends SubscriptionIdentity> createSubscription(
            @NonNull Platform platform,
            @NonNull String subscriptionType,
            @NonNull ImmutableMap<String, String> conditions);

    @NonNull Mono<Nil> revokeSubscription(@NonNull Platform platform, @NonNull String subscriptionId);

    @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllSubscriptions(@NonNull Platform platform);


    @NonNull Mono<Nil> deleteUserSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData);



}
