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
import perobobbot.lang.fp.Value2;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SubscriptionSynchronizer {

    private final @NonNull EventSubManager eventSubManager;
    private final @NonNull
    @EventService
    SubscriptionService subscriptionService;

    @Scheduled(fixedDelay = 600_000)
    public void synchronize() {
        eventSubManager.cleanFailedSubscription()
                       .flatMap(this::synchronizePlatform, 5)
                       .subscribe();

    }

    private Mono<Nil> synchronizePlatform(@NonNull Platform platform) {
        final var persisted = subscriptionService.listAllByPlatform(platform);

        return eventSubManager.listAllSubscriptions(platform)
                              .flatMap(existing -> synchronizePlatform(platform, existing, persisted));

    }

    private Mono<Nil> synchronizePlatform(
            @NonNull Platform platform,
            @NonNull ImmutableList<SubscriptionIdentity> valid,
            @NonNull ImmutableList<SubscriptionView> persisted) {

        final var match = Matcher.match(valid, persisted);

        final List<Mono<Nil>> todo = new ArrayList<>();


        match.getToUpdateSubs().entrySet().stream()
             .map(e -> Mono.fromCallable(() -> {
                 subscriptionService.updateSubscriptionId(e.getKey(), e.getValue());
                 return Nil.NIL;
             }))
             .forEach(todo::add);


        match.getToRevokeSubs()
             .stream()
             .map(id -> eventSubManager.revokeSubscription(platform,id))
             .forEach(todo::add);


        match.getToRefreshSubs()
             .stream()
             .map(r -> performRefresh(platform,r))
             .forEach(todo::add);

        return Mono.when(todo).map(v -> Nil.NIL);
    }

    private @NonNull Mono<Nil> performRefresh(@NonNull Platform platform, @NonNull SubscriptionView subscriptionViewToRefresh) {
        final var subscriptionType = subscriptionViewToRefresh.subscriptionType();
        final var conditions = subscriptionViewToRefresh.conditionMap();
        final var subscriptionDbId = subscriptionViewToRefresh.id();
        return eventSubManager.createSubscription(platform, subscriptionType, conditions)
                              .map(i -> {
                                  subscriptionService.updateSubscriptionId(subscriptionDbId, i.subscriptionId());
                                  return Nil.NIL;
                              });
    }

}
