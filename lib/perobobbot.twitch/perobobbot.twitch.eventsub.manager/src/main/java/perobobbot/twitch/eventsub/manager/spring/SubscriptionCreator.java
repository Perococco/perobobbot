package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.SubscriptionService;
import perobobbot.lang.Platform;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.ConditionId;
import perobobbot.twitch.eventsub.api.ObjectWithLogin;
import perobobbot.twitch.eventsub.api.TwitchSubscription;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import perobobbot.twitch.eventsub.manager.EventSubRequestToTwitch;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SubscriptionCreator implements Link<ObjectWithLogin<Subscription>, Mono<UserSubscriptionView>> {

    private final @NonNull EventSubRequestToTwitch eventSubRequestToTwitch;

    private final @NonNull SubscriptionService subscriptionService;

    @Override
    public @NonNull Mono<UserSubscriptionView> call(@NonNull ObjectWithLogin<Subscription> parameter, @NonNull Chain<ObjectWithLogin<Subscription>, Mono<UserSubscriptionView>> chain) {
        return getOrCreateSubscription(parameter)
                .flatMap(uuid -> addLoginToSubscription(uuid, parameter.login()));

    }

    private @NonNull Mono<UUID> getOrCreateSubscription(@NonNull ObjectWithLogin<Subscription> parameter) {
        final var subscription = parameter.value();
        final var subscriptionType = subscription.getType().getIdentification();
        final var conditionId = subscription.getConditionId();

        final Optional<SubscriptionView> existing = subscriptionService.findSubscription(Platform.TWITCH, subscriptionType, conditionId);
        return existing.map(SubscriptionView::id)
                       .map(Mono::just)
                       .orElseGet(() -> createSubscription(subscription).map(SubscriptionView::id));

    }

    private @NonNull Mono<SubscriptionView> createSubscription(Subscription subscription) {
        return eventSubRequestToTwitch.subscribeToEventSub(subscription)
                                      .map(this::createSubscription);
    }

    private @NonNull SubscriptionView createSubscription(@NonNull TwitchSubscription twitchSubscription) {
        return subscriptionService.createSubscription(
                Platform.TWITCH,
                twitchSubscription.getId(),
                twitchSubscription.getType().getIdentification(),
                new ConditionId(twitchSubscription.getCondition()).toString()
                );
    }

    private @NonNull Mono<UserSubscriptionView> addLoginToSubscription(@NonNull UUID subscriptionId, @NonNull String login) {
        final UserSubscriptionView view = subscriptionService.addUserToSubscription(subscriptionId, login);
        return Mono.just(view);
    }
}
