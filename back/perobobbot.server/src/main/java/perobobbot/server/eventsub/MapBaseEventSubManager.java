package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.eventsub.EventSubManager;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.eventsub.SubscriptionData;
import perobobbot.lang.Nil;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MapBaseEventSubManager implements EventSubManager {

    public static @NonNull EventSubManager create(@NonNull ImmutableCollection<PlatformEventSubManager> managers) {
        final var managerPerPlatform = managers.stream().collect(ImmutableMap.toImmutableMap(PlatformEventSubManager::getPlatform, m -> m));
        return new MapBaseEventSubManager(managerPerPlatform);
    }


    private final @NonNull ImmutableMap<Platform, PlatformEventSubManager> managerPerPlatform;

    @Override
    public @NonNull Set<String> getSubscriptionTypes(@NonNull Platform platform) {
        return getManager(platform).getSubscriptionTypes();
    }

    @Override
    public @NonNull Mono<Nil> deleteUserSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId) {
        return getManager(platform).deleteUserSubscription(login, subscriptionId);
    }

    @Override
    public @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData) {
        return getManager(subscriptionData.getPlatform()).createUserSubscription(login,
                subscriptionData.getSubscriptionType(),
                subscriptionData.getCondition());
    }

    private @NonNull PlatformEventSubManager getManager(@NonNull Platform platform) {
        final var manager = managerPerPlatform.get(platform);
        if (manager == null) {
            throw new PerobobbotException("No PlatformEventSubManager for platform '" + platform + "'");
        }
        return manager;
    }

    public boolean isPlatformAvailable(@NonNull Platform platform) {
        return managerPerPlatform.containsKey(platform);
    }

    @Override
    public @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllSubscriptions(@NonNull Platform platform) {
        return getManager(platform).listAllValidSubscriptions();
    }

    @Override
    public @NonNull Mono<Nil> revokeSubscription(@NonNull Platform platform, @NonNull String subscriptionId) {
        return getManager(platform).revokeSubscription(subscriptionId);
    }

    @Override
    public @NonNull Mono<? extends SubscriptionIdentity> createSubscription(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull ImmutableMap<String, String> conditions) {
        return getManager(platform).createSubscription(subscriptionType,conditions);
    }

    @Override
    public @NonNull Flux<Platform> cleanFailedSubscription() {
        final var cleaner = managerPerPlatform.values()
                                              .stream()
                                              .map(PlatformEventSubManager::cleanFailedSubscription)
                                              .toList();

        return Flux.concat(cleaner);
    }

}
