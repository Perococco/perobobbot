package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.service.SubscriptionService;
import perobobbot.lang.Nil;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.ObjectWithLogin;
import perobobbot.twitch.eventsub.manager.EventSubRequestToTwitch;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class SubscriptionTerminator implements Link<ObjectWithLogin<UUID>, Mono<Nil>> {

    private final @NonNull EventSubRequestToTwitch eventSubRequestToTwitch;
    private final @NonNull SubscriptionService subscriptionService;

    @Override
    public Mono<Nil> call(@NonNull ObjectWithLogin<UUID> parameter, @NonNull Chain<ObjectWithLogin<UUID>, Mono<Nil>> chain) {
        subscriptionService.deleteUserSubscription(parameter.value(), parameter.login());
        final var deletedSubscription = subscriptionService.cleanSubscription(parameter.value());

        return deletedSubscription.map(SubscriptionView::subscriptionId)
                                  .map(eventSubRequestToTwitch::deleteSubscription)
                                  .map(m -> m.map(o -> Nil.NIL))
                                  .orElseGet(() -> Mono.just(Nil.NIL));

    }
}
