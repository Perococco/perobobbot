package perobobbot.twutch.eventsub.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookManager;
import perobobbot.http.WebHookSubscription;
import perobobbot.lang.Nil;
import perobobbot.lang.RandomString;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.*;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Mono;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
public class SimpleEventSubManager implements EventSubManager {

    private final TwitchRequestSaver twitchRequestSaver = new TwitchRequestSaver();

    private final String eventSubPath = "/eventsub";
    private final String secret = RandomString.generate(50);

    private final @NonNull TwitchService twitchService;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull ObjectMapper objectMapper;

    private WebHookSubscription webHookSubscription = null;

    //TODO make the access to this resource thread safe
    private final Map<String, SubscriptionInfo> subscriptions = new HashMap<>();

    @Synchronized
    public void start() {
        webHookSubscription = webHookManager.addListener(eventSubPath, this::onCall).orElse(null);
    }

    @Synchronized
    public void stop() {
        if (webHookSubscription != null) {
            webHookSubscription.unsubscribe();
            webHookSubscription = null;
        }
    }

    public void onCall(@NonNull String path,
                       @NonNull RequestMethod method,
                       @NonNull HttpServletRequest request,
                       @NonNull HttpServletResponse response) throws IOException, ServletException {

        final var validatedRequest = TwitchRequestValidator.validate(request, secret).orElse(null);
        if (validatedRequest == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);

        twitchRequestSaver.saveBody(validatedRequest.content());

        final var content = TwitchRequestDeserializer.deserialize(objectMapper,validatedRequest.type(),validatedRequest.content())
                .orElse(null);

        if (content == null) {
            LOG.error(Markers.EVENT_SUB_MARKER, "Failed to deserialized Twitch request content");
            return;
        }

        content.accept(new EvenSubRequest.Visitor() {

            @Override
            public void visit(@NonNull EventSubNotification notification) {
                System.out.println("Received notification "+notification);
            }

            @Override
            public void visit(@NonNull EventSubVerification verification) {
                LOG.info("Send challenge : {}", verification.getChallenge());
                try {
                    response.getOutputStream().print(verification.getChallenge());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        });

    }

    @Value
    private static class SubscriptionInfo {
        @NonNull Subscription subscription;
        @NonNull TwitchSubscription twitchSubscription;
    }

    @Override
    public @NonNull Mono<TwitchSubscription> subscribe(@NonNull Subscription subscription) {

        final var webHookSubscription = this.webHookSubscription;
        if (webHookSubscription == null) {
            LOG.error("No webhook available. Cannot subscribe to an EventSub");
            return Mono.error(new IllegalStateException("No webhook available. Cannot subscribe to an EventSub"));
        }

        final var transport = new TransportRequest(TransportMethod.WEBHOOK,
                                                   webHookSubscription.getWebHookCallbackURI().toString(),
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
                                subscriptions.put(data.getId(), new SubscriptionInfo(subscription, data));
                            });
    }

    @Override
    public @NonNull Mono<Nil> deleteSubscription(@NonNull String id) {
        final var subscriptionInfo = subscriptions.get(id);
        if (subscriptionInfo == null) {
            return twitchService.deleteEventSubSubscription(id).doOnSuccess(n -> {
                subscriptions.remove(id);
            });
        }
        return Mono.just(Nil.NIL);
    }

    @Override
    public @NonNull Mono<TwitchSubscriptionData> listSubscriptions() {
        return twitchService.getEventSubSubscriptions();
    }
}
