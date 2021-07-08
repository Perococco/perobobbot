package perobobbot.eventsub;

import lombok.NonNull;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserEventSubManager {

    @NonNull Mono<Nil> deleteUserSubscription(@NonNull Platform platform, @NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login, @NonNull SubscriptionData subscriptionData);


}
