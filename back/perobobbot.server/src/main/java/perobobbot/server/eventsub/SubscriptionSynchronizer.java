package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionSynchronizer {

    private final @NonNull EventSubManager eventSubManager;
    private final @NonNull
    @EventService
    SubscriptionService subscriptionService;

    @Scheduled(fixedDelay = 60_000)
    public void synchronize() {
        Mono.when(Platform.stream()
                          .filter(eventSubManager::isPlatformManaged)
                          .map(this::synchronizePlatform)
                          .toList())
            .subscribe();

    }

    private Mono<Nil> synchronizePlatform(@NonNull Platform platform) {
        final var persisted = subscriptionService.listAllByPlatform(platform);

        return eventSubManager.listAllSubscriptions(platform)
                              .flatMap(existing -> synchronizePlatform(platform, existing, persisted));

    }

    private Mono<Nil> synchronizePlatform(
            @NonNull Platform platform,
            @NonNull ImmutableList<SubscriptionIdentity> onPlatform,
            @NonNull ImmutableList<SubscriptionView> persisted) {



        final var match = Matcher.match(onPlatform, persisted);

        final List<Mono<Nil>> todo = new ArrayList<>();

        match.getToUpdateSubs().entrySet().stream()
             .map(e -> Mono.fromCallable(() -> {
                 subscriptionService.setSubscriptionPlatformId(e.getKey(), e.getValue());
                 return Nil.NIL;
             }))
             .forEach(todo::add);


        match.getToRevokeSubs()
             .stream()
             .map(id -> eventSubManager.revokeSubscription(platform, id))
             .forEach(todo::add);


        match.getToRefreshSubs()
             .stream()
             .map(r -> performRefresh(platform, r))
             .forEach(todo::add);

        return Mono.when(todo).map(v -> Nil.NIL);
    }

    private @NonNull Mono<Nil> performRefresh(@NonNull Platform platform, @NonNull SubscriptionView subscriptionViewToRefresh) {
        final var subscriptionType = subscriptionViewToRefresh.getSubscriptionType();
        final var conditions = subscriptionViewToRefresh.getConditions();
        final var subscriptionDbId = subscriptionViewToRefresh.getId();
        return eventSubManager.createSubscription(platform, subscriptionType, conditions)
                              .map(i -> {
                                  subscriptionService.setSubscriptionPlatformId(subscriptionDbId, i.getSubscriptionId());
                                  return Nil.NIL;
                              });
    }

}
