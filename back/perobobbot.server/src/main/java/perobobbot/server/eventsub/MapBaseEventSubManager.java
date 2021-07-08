package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.eventsub.EventSubManager;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.Set;

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

    public @NonNull Optional<PlatformEventSubManager> findManager(@NonNull Platform platform) {
        return Optional.ofNullable(managerPerPlatform.get(platform));
    }

    public boolean isPlatformManaged(@NonNull Platform platform) {
        return managerPerPlatform.containsKey(platform);
    }



//    @Override
//    public @NonNull Mono<Nil> deleteUserSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId) {
//        return getManager(platform).deleteUserSubscription(login, subscriptionId);
//    }
//
//    @Override
//    public @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData) {
//        return getManager(subscriptionData.getPlatform()).createUserSubscription(login,
//                subscriptionData.getSubscriptionType(),
//                subscriptionData.getCondition());
//    }


}
