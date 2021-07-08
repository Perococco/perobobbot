package perobobbot.twitch.eventsub.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.lang.Conditions;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.SubscriptionType;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.subscription.GenericSubscription;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
public class TwitchEventSubManager implements PlatformEventSubManager {

    private final @NonNull TwitchEventSubSubscriber eventSubSubscriber;
    private final @NonNull TwitchService twitchService;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull ImmutableSet<String> getSubscriptionTypes() {
        return SubscriptionType.getIdentifications();
    }


    @Override
    public @NonNull Mono<Nil> revokeSubscription(@NonNull String subscriptionId) {
        return twitchService.deleteEventSubSubscription(subscriptionId);
    }

    @Override
    public @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllSubscriptions() {
        return listAllSubscription().map(d -> Arrays.stream(d.getData())
                                                    .collect(ImmutableList.toImmutableList()));
    }

    @Override
    public @NonNull Mono<? extends SubscriptionIdentity> createSubscription(@NonNull String subscriptionType,
                                                                            @NonNull Conditions conditions) {
        return eventSubSubscriber.subscribeToEventSub(GenericSubscription.from(subscriptionType,conditions));
    }

    private @NonNull Mono<TwitchSubscriptionData> listAllSubscription() {
        return twitchService.getEventSubSubscriptions();
    }



    //    @Override
//    public @NonNull Mono<Nil> deleteUserSubscription(@NonNull String login, @NonNull UUID subscriptionId) {
//        return eventSubHandler.handleSubscriptionDeletion(login, subscriptionId);
//    }
//
//    @Override
//    public @NonNull Mono<UserSubscriptionView> createUserSubscription(@NonNull String login, @NonNull String subscriptionType, @NonNull ImmutableMap<String, String> condition) {
//        final var type = IdentifiedEnumTools.getEnum(subscriptionType, SubscriptionType.class);
//        final Subscription subscription = type.create(condition);
//        return eventSubHandler.handleCreateSubscription(login, subscription);
//    }
//

//    @Override
//    public @NonNull Mono<Platform> cleanFailedSubscription() {
//        return listAllSubscription()
//                .map(d -> Arrays.stream(d.getData())
//                    .filter(TwitchSubscription::isFailure)
//                    .map(TwitchSubscription::getId)
//                    .collect(ImmutableSet.toImmutableSet())
//                )
//                .flatMap(this::clean)
//                .map(a -> Platform.TWITCH);
//    }

//    private @NonNull Mono<?> clean(@NonNull ImmutableSet<String> failedSubscriptionIds) {
//        if (failedSubscriptionIds.isEmpty()) {
//            return Mono.just(Nil.NIL);
//        }
//
//        final List<Mono<Nil>> deletions = new ArrayList<>(failedSubscriptionIds.size());
//        for (String failedSubscriptionId : failedSubscriptionIds) {
//            deletions.add(twitchService.deleteEventSubSubscription(failedSubscriptionId));
//        }
//
//        return Mono.when(deletions);
//    }


}
