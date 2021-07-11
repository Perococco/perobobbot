package perobobbot.server.eventsub;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.eventsub.UserEventSubManager;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.lang.SubscriptionData;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
@PluginService(type = UserEventSubManager.class, apiVersion = UserEventSubManager.VERSION, sensitive = true)
public class SpringUserEventSubManager implements UserEventSubManager {

    private final @NonNull EventSubManager eventSubManager;
    private final @NonNull
    @EventService
    SubscriptionService subscriptionService;

    @Override
    public @NonNull Mono<Nil> deleteUserSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId) {
        subscriptionService.removeUserFromSubscription(subscriptionId, login);
        final var removed = subscriptionService.cleanSubscription(subscriptionId).orElse(null);

        if (removed == null) {
            return Mono.just(Nil.NIL);
        }

        return eventSubManager.revokeSubscription(removed.getPlatform(), removed.getSubscriptionId());
    }

    private final Set<UUID> processed = Collections.synchronizedSet(new HashSet<>());

    @Override
    public @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData) {
        return Mono.defer(() -> {
            final var subscription = subscriptionService.getOrCreateSubscription(subscriptionData);
            final var dbId = subscription.getId();

            final var attachUserToSubscription =
                    Mono.fromCallable(() -> subscriptionService.addUserToSubscription(subscription.getId(), login));

            {
                final var platformId = subscription.getSubscriptionId();
                if (!platformId.isEmpty()) {
                    return attachUserToSubscription;
                }
            }

            final boolean shouldProcess = processed.add(subscription.getId());
            if (!shouldProcess) {
                return attachUserToSubscription;
            }


            final var createSubscription = eventSubManager.createSubscription(subscriptionData)
                                                          .map(SubscriptionIdentity::getSubscriptionId)
                                                          .map(id -> {
                                                              subscriptionService.setSubscriptionPlatformId(dbId,id);
                                                              return Nil.NIL;
                                                          });

            return Mono.zip(createSubscription, attachUserToSubscription,(nil,view) -> view);

        });
    }
}
