package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.lang.Platform;
import perobobbot.lang.Todo;

@Component
@RequiredArgsConstructor
public class EventSubSynchronizer {

    private final @NonNull EventSubManager eventSubManager;
    private final @NonNull @EventService SubscriptionService subscriptionService;

    public void synchronize() {
    }

    private void synchronizePlatform(@NonNull Platform platform) {
        final var persisted = subscriptionService.listAllByPlatform(platform);
        eventSubManager.listAllSubscriptions(platform)
                       .subscribe(existing -> synchronize(existing,persisted));

    }

    private void synchronize(@NonNull ImmutableList<SubscriptionIdentity> existing,
                             @NonNull ImmutableList<SubscriptionView> persisted) {
        Todo.TODO();
    }
}
