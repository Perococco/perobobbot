package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.lang.CommonConfig;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.lang.ThrowableTool;
import perobobbot.server.config.externaluri.ExternalURIProvider;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class SubscriptionSynchronizer {


    @Qualifier("webhook")
    private final @NonNull ExternalURIProvider oauthExternalURIProvider;
    private final @NonNull EventSubManager eventSubManager;
    private final @NonNull @EventService ClientService clientService;
    private final @NonNull
    @EventService
    SubscriptionService subscriptionService;


    @Scheduled(fixedDelay = 3600_000)
    public void synchronize() {
        if (CommonConfig.isNoSubscriptionSync()) {
            return;
        }
        try {
            final var callbackUrlChecker = checkCallbackUrl();
            final var synchronizer = Mono.when(Platform.stream()
                                                       .filter(eventSubManager::isPlatformManaged)
                                                       .filter(clientService::hasClientForPlatform)
                                                       .map(this::synchronizePlatform)
                                                       .toList());

            callbackUrlChecker.then(synchronizer)
                              .subscribe(v -> {}, this::handleError);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.error("Subscription synchronization failed : {}", t.getMessage());
        }

    }

    private void handleError(@NonNull Throwable throwable) {
        LOG.error("Subscription synchronization failed : {}", throwable.getMessage());
    }


    private Mono<?> checkCallbackUrl() {
        final var webhookHost = oauthExternalURIProvider.get().toURI();
        return Mono.fromRunnable(() -> subscriptionService.clearCallbackUrlIfDoesNotStartWith(webhookHost));
    }

    private Mono<Nil> synchronizePlatform(@NonNull Platform platform) {
        return eventSubManager.listAllSubscriptions(platform)
                              .flatMap(existing -> synchronizePlatform(platform, existing));

    }

    private Mono<Nil> synchronizePlatform(
            @NonNull Platform platform,
            @NonNull ImmutableList<SubscriptionIdentity> onPlatform) {

        final var persisted = subscriptionService.listAllByPlatform(platform);

        final var match = Matcher.match(onPlatform, persisted);

        if (match.hasAnyChange()) {
            LOG.info("Synchronize Subscriptions : Rf:{} Up:{} Rk:{}", match.getToRefreshSubs().size(), match.getToUpdateSubs().size(), match.getToRevokeSubs().size());
        }


        final List<Mono<Nil>> todo = new ArrayList<>();

        match.getToUpdateSubs().entrySet().stream()
             .map(e -> Mono.fromCallable(() -> {
                 subscriptionService.updateSubscriptionWithPlatformAnswer(e.getKey(), e.getValue());
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
                              .map(subscriptionIdentity -> {
                                  subscriptionService.updateSubscriptionWithPlatformAnswer(subscriptionDbId, subscriptionIdentity);
                                  return Nil.NIL;
                              });
    }

}
