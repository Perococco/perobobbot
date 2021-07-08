package perobobbot.twitch.eventsub.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.ClientService;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.lang.Todo;
import perobobbot.oauth.ClientApiToken;
import perobobbot.twitch.client.api.TwitchEventSubLister;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceWithToken;
import perobobbot.twitch.eventsub.api.EventSubHandler;
import perobobbot.twitch.eventsub.api.SubscriptionType;
import perobobbot.twitch.eventsub.api.TwitchSubscription;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Flow;
import java.util.function.Function;

@RequiredArgsConstructor
public class TwitchEventSubManager implements PlatformEventSubManager {

    private final @NonNull EventSubHandler eventSubHandler;
    private final @NonNull TwitchService twitchService;
    private final @NonNull ClientService clientService;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull ImmutableSet<String> getSubscriptionTypes() {
        return SubscriptionType.getIdentifications();
    }

    @Override
    public @NonNull Mono<Nil> deleteSubscription(@NonNull String login, @NonNull UUID subscriptionId) {
        return eventSubHandler.handleSubscriptionDeletion(login, subscriptionId);
    }

    @Override
    public @NonNull Mono<UserSubscriptionView> createSubscription(@NonNull String login, @NonNull String subscriptionType, @NonNull ImmutableMap<String, String> condition) {
        final var type = IdentifiedEnumTools.getEnum(subscriptionType, SubscriptionType.class);
        final Subscription subscription = type.create(condition);
        return eventSubHandler.handleCreateSubscription(login, subscription);
    }


    @Override
    public Mono<Nil> cleanFailedSubscription() {
        return listAllSubscription()
                .map(d -> Arrays.stream(d.getData())
                    .filter(TwitchSubscription::isFailure)
                    .map(TwitchSubscription::getId)
                    .collect(ImmutableSet.toImmutableSet())
                )
                .flatMap(this::clean)
                .map(a -> Nil.NIL);
    }

    @Override
    public @NonNull Mono<Nil> revokeSubscription(@NonNull String subscriptionId) {
        return twitchService.deleteEventSubSubscription(subscriptionId);
    }

    public @NonNull Mono<?> clean(@NonNull ImmutableSet<String> failedSubscriptionIds) {
        if (failedSubscriptionIds.isEmpty()) {
            return Mono.just(Nil.NIL);
        }

        final List<Mono<Nil>> deletions = new ArrayList<>(failedSubscriptionIds.size());
        for (String failedSubscriptionId : failedSubscriptionIds) {
            deletions.add(twitchService.deleteEventSubSubscription(failedSubscriptionId));
        }

        return Mono.when(deletions);
    }


    @Override
    public @NonNull Mono<ImmutableList<SubscriptionIdentity>> listAllValidSubscriptions() {
        return listAllSubscription().map(d -> Arrays.stream(d.getData())
                                                    .filter(TwitchSubscription::isValid)
                                                    .collect(ImmutableList.toImmutableList()));

    }

    private @NonNull Mono<TwitchSubscriptionData> listAllSubscription() {
        return twitchService.getEventSubSubscriptions();
    }
}
