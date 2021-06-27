package perobobbot.eventsub;

import lombok.NonNull;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

public interface EventSubManager {

    @NonNull Set<String> getSubscriptionTypes(@NonNull Platform platform);

    @NonNull Mono<Nil> deleteSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> createSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData);

}
