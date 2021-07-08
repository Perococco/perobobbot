package perobobbot.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.lang.Conditions;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

/**
 * Methods to create/list/revoke methods on a platform.
 * They do not and should not have any effect an the DB
 */
public interface EventSubManager {

    /**
     * @param platform a platform
     * @return true if a manager exists for event sub for the provided platform
     */
    boolean isPlatformManaged(@NonNull Platform platform);

    /**
     * find a platform specific manager
     * @param platform the platform
     * @return an optional containing the manager for the provided platform if one exists, an empty optional
     * otherwise
     */
    @NonNull Optional<PlatformEventSubManager> findManager(@NonNull Platform platform);

    @NonNull Set<String> getSubscriptionTypes(@NonNull Platform platform);


    default @NonNull PlatformEventSubManager getManager(@NonNull Platform platform) {
        return findManager(platform).orElseThrow(() -> new EventSubUnmanagedPlatform(platform));
    }

    default @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllSubscriptions(@NonNull Platform platform) {
        return getManager(platform).listAllSubscriptions();
    }

    default @NonNull Mono<? extends SubscriptionIdentity> createSubscription(
            @NonNull Platform platform,
            @NonNull String subscriptionType,
            @NonNull Conditions conditions) {
        return getManager(platform).createSubscription(subscriptionType,conditions);
    }

    default @NonNull Mono<Nil> revokeSubscription(@NonNull Platform platform, @NonNull String subscriptionId) {
        return getManager(platform).revokeSubscription(subscriptionId);
    }



}
