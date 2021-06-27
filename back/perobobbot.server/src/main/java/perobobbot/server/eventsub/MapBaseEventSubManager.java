package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.eventsub.EventSubManager;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.eventsub.SubscriptionData;
import perobobbot.lang.Nil;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MapBaseEventSubManager implements EventSubManager {

    public static @NonNull EventSubManager create(@NonNull ImmutableCollection<PlatformEventSubManager> managers) {
        final var managerPerPlatform = managers.stream().collect(ImmutableMap.toImmutableMap(PlatformEventSubManager::getPlatform, m -> m));
        return new MapBaseEventSubManager(managerPerPlatform);
    }


    private final ImmutableMap<Platform, PlatformEventSubManager> managerPerPlatform;

    @Override
    public @NonNull Set<String> getSubscriptionTypes(@NonNull Platform platform) {
        return getManager(platform).getSubscriptionTypes();
    }

    @Override
    public @NonNull Mono<Nil> deleteSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId) {
        return getManager(platform).deleteSubscription(login,subscriptionId);
    }

    @Override
    public @NonNull Mono<UserSubscriptionView> createSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData) {
        return getManager(subscriptionData.getPlatform()).createSubscription(login,
                                                                             subscriptionData.getSubscriptionType(),
                                                                             subscriptionData.getCondition());
    }

    private @NonNull PlatformEventSubManager getManager(@NonNull Platform platform) {
        final var manager = managerPerPlatform.get(platform);
        if (manager == null) {
            throw new PerobobbotException("No PlatformEventSubManager for platform '"+platform+"'");
        }
        return manager;
    }
}
