package perobobbot.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.lang.Conditions;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import reactor.core.publisher.Mono;

public interface PlatformEventSubManager {

    /**
     * @return the platform this manager applies to
     */
    @NonNull Platform getPlatform();

    /**
     * @return the type of subscription the platform provides
     */
    @NonNull ImmutableSet<String> getSubscriptionTypes();


    @NonNull Mono<? extends SubscriptionIdentity> createSubscription(@NonNull String subscriptionType,
                                                                     @NonNull Conditions conditions);

    @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllSubscriptions();

    @NonNull Mono<Nil> revokeSubscription(@NonNull String subscriptionId);


    //    /**
//     * @param login          the login of the user
//     * @param subscriptionId the id of the subscription the user is attached to
//     * @return
//     */
//    @NonNull Mono<Nil> deleteUserSubscription(@NonNull String login, @NonNull UUID subscriptionId);
//
//    @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login,
//                                                               @NonNull String subscriptionType,
//                                                               @NonNull ImmutableMap<String, String> conditions);
//







}
