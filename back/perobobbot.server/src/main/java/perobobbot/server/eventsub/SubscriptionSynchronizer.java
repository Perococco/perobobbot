package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.lang.Todo;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionSynchronizer {

    private final @NonNull EventSubManager eventSubManager;
    private final @NonNull
    @EventService
    SubscriptionService subscriptionService;

//    @Scheduled(fixedDelay = 600_000)
    public void synchronize() {
        eventSubManager.cleanFailedSubscription()
                       .flatMap(this::synchronizePlatform,5)
                       .subscribe();

    }

    private Mono<Nil> synchronizePlatform(@NonNull Platform platform) {
        final var persisted = subscriptionService.listAllByPlatform(platform);

        return eventSubManager.listAllSubscriptions(platform)
                       .flatMap(existing -> synchronizePlatform(existing, persisted));

    }

    private Mono<Nil> synchronizePlatform(@NonNull ImmutableList<SubscriptionIdentity> valid,
                                     @NonNull ImmutableList<SubscriptionView> persisted) {

        final var validId = valid.stream().map(SubscriptionIdentity::subscriptionId).collect(ImmutableSet.toImmutableSet());
        final var persistedId = persisted.stream().map(SubscriptionView::subscriptionId).collect(ImmutableSet.toImmutableSet());
        return Mono.just(Nil.NIL);
    }



    private record Key(@NonNull String subscriptionType, @NonNull String conditionId) {
    }
}
