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
import perobobbot.twitch.eventsub.api.EventSubManager;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Log4j2
public class SimpleEventSubManager implements EventSubManager {

    private final String eventSubPath = "/eventsub";
    private final String secret = RandomString.generate(50);
    private final String macAlgorithm = "HmacSHA256";

    private final @NonNull TwitchService twitchService;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull ObjectMapper objectMapper;

    private WebHookSubscription webHookSubscription;

    //TODO make the access to this resource thread safe
    private final Map<String, SubscriptionInfo> subscriptions = new HashMap<>();

    @Synchronized
    public void start() {
        webHookSubscription = webHookManager.addListener(eventSubPath,this::onCall);
    }

    @Synchronized
    public void stop() {
        webHookSubscription.unsubscribe();
        webHookSubscription = null;
    }

    public void onCall(@NonNull String path,
                       @NonNull RequestMethod method,
                       @NonNull HttpServletRequest request,
                       @NonNull HttpServletResponse response) throws IOException, ServletException {

        final var messageId = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_ID.getHeader(request).orElse(null);
        final var timeStamp = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_TIMESTAMP.getHeader(request).orElse(null);
        final var type = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_TYPE.getHeader(request).orElse(null);
        final var signature = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_SIGNATURE.getHeader(request).orElse(null);

        LOG.info("Received request from Twitch");
        LOG.info(" messageId : {} ",messageId);
        LOG.info(" timeStamp : {} ",timeStamp);
        LOG.info(" type      : {} ",type);
        LOG.info(" signature : {} ",signature);

        if (messageId == null || timeStamp == null || type == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        final var bodyContent = request.getInputStream().readAllBytes();

        final String computedSignature;
        try {
            final var mac = Mac.getInstance(macAlgorithm);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.US_ASCII),macAlgorithm));
            mac.update(messageId.getBytes(StandardCharsets.US_ASCII));
            mac.update(timeStamp.getBytes(StandardCharsets.US_ASCII));
            mac.update(bodyContent);
            final var signatureBytes = mac.doFinal();
            computedSignature = IntStream.range(0,signatureBytes.length).mapToObj(i -> String.format("%02x",signatureBytes[i])).collect(
                    Collectors.joining("","sha256=",""));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ServletException("Could not find MAC algorithm "+macAlgorithm+" to check Twitch message signature",e);
        }
        LOG.info(" Computed signature : {}",computedSignature);

        if (!computedSignature.equals(signature)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);

        switch (type) {
            case "notification" -> {
                System.out.println("TODO broadcast notification");
            }
            case "webhook_callback_verification" -> {
                final var verification = objectMapper.readValue(bodyContent,EventSubVerification.class);
                LOG.info("Send challenge : {}",verification.getChallenge());
                response.getOutputStream().print(verification.getChallenge());
            }
        }
    }

    @Value
    private static class SubscriptionInfo {
        @NonNull Subscription subscription;
        @NonNull TwitchSubscription twitchSubscription;
    }

    @Override
    public @NonNull Mono<TwitchSubscription> subscribe(@NonNull Subscription subscription) {

        final var transport = new TransportRequest(TransportMethod.WEBHOOK, webHookSubscription.getWebHookCallbackURI().toString(), secret);

        final var request = TwitchSubscriptionRequest.builder()
                                                     .type(subscription.getType())
                                                     .version(subscription.getVersion())
                                                     .condition(subscription.getCondition())
                                                     .transport(transport)
                                                     .build();

        return twitchService.subscriptToEventSub(request)
                            .map(t -> t.getData()[0])
                            .doOnSuccess(data -> {
                                LOG.info("Received subscription acknowledgment from Twitch : '{}'",data.getId());
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
