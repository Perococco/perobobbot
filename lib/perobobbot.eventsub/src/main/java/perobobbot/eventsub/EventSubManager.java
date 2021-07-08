package perobobbot.eventsub;

import com.google.common.collect.ImmutableList;
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

    @NonNull Mono<Nil> deleteSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> createSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData);


    @NonNull Flux<Platform> cleanFailedSubscription();

    @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllSubscriptions(@NonNull Platform platform);

}
