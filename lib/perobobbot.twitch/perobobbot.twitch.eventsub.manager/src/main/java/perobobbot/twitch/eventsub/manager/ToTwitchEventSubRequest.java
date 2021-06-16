package perobobbot.twitch.eventsub.manager;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.Nil;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.*;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Log4j2
public class ToTwitchEventSubRequest {

    private @NonNull Supplier<Optional<URI>> callbackURISupplier;
    private final @NonNull String secret;

    private final @NonNull TwitchService twitchService;


    public @NonNull Mono<TwitchSubscription> subscribeToEventSub(@NonNull Subscription subscription) {

        final var callbackURI = this.callbackURISupplier.get().orElse(null);
        if (callbackURI == null) {
            LOG.error("No webhook available. Cannot subscribe to an EventSub");
            return Mono.error(new IllegalStateException("No webhook available. Cannot subscribe to an EventSub"));
        }

        final var transport = new TransportRequest(TransportMethod.WEBHOOK,
                                                   callbackURI.toString(),
                                                   secret);

        final var request = TwitchSubscriptionRequest.builder()
                                                     .type(subscription.getType())
                                                     .version(subscription.getVersion())
                                                     .condition(subscription.getCondition())
                                                     .transport(transport)
                                                     .build();

        return twitchService.subscriptToEventSub(request)
                            .map(t -> t.getData()[0])
                            .doOnSuccess(data -> {
                                LOG.info("Received subscription acknowledgment from Twitch : '{}'", data.getId());
                            });
    }

    public @NonNull Mono<Nil> deleteSubscription(@NonNull String id) {
            return twitchService.deleteEventSubSubscription(id);
    }

}
