package perobobbot.server.eventsub;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.eventsub.UserEventSubManager;
import perobobbot.lang.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        final var subscription = subscriptionService.getOrCreateSubscription(subscriptionData);

        final boolean shouldProcess = processed.add(subscription.getId());

        if (!shouldProcess) {
            return Mono.just(subscriptionService.addUserToSubscription(subscription.getId(), login));
        }
        return Todo.TODO();

    }
}
