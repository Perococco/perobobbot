package perobobbot.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

public interface PlatformEventSubManager {


    @NonNull Platform getPlatform();

    @NonNull ImmutableSet<String> getSubscriptionTypes();

    @NonNull Mono<Nil> deleteSubscription(@NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Mono<UserSubscriptionView> createSubscription(@NonNull String login,
                                                           @NonNull String subscriptionType,
                                                           @NonNull ImmutableMap<String,String> condition);

    @NonNull Mono<Nil> cleanFailedSubscription();

    @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllValidSubscriptions();

}
