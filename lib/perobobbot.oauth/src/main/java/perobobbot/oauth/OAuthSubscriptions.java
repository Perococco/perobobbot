package perobobbot.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookManager;
import perobobbot.http.WebHookSubscription;
import perobobbot.lang.IdKey;
import perobobbot.lang.IdMap;
import perobobbot.lang.Instants;
import perobobbot.lang.SmartLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class OAuthSubscriptions {

    public static final long TIME_OUT_CLEAN_UP = 120_000;

    public static final String STATE_PARAMETER_NAME = "state";

    private static final Duration TIMEOUT_DURATION = Duration.ofMinutes(Integer.getInteger("oauth.user.time-out", 5));

    private final @NonNull Instants instants;
    private final @NonNull WebHookManager webHookManager;

    private final SmartLock lock = SmartLock.reentrant();

    private final IdMap<String, Data> subscriptions = new IdMap<>();

    private final Map<String, WebHookSubscription> subscriptionPerPath = new HashMap<>();


    public void removeAll() {
        lock.runLocked(subscriptions::clear);
    }

    @Scheduled(fixedRate = TIME_OUT_CLEAN_UP)
    public void handleTimeOut() {
        final var now = instants.now();
        this.subscriptions.removeIf(d -> d.isTimedOut(now), Data::timeOut);
    }

    @Synchronized
    public void dispose() {
        subscriptionPerPath.values().forEach(WebHookSubscription::unsubscribe);
        subscriptionPerPath.clear();
        subscriptions.removeIf(d -> true, Data::interrupt);
    }

    private @NonNull Optional<Data> getData(@NonNull IdKey<String> idKey) {
        return lock.getLocked(() -> this.subscriptions.freeId(idKey));
    }

    public @NonNull OauthSubscriptionData subscribe(@NonNull String path, @NonNull OAuthListener oAuthListener) {
        final var idBooking = this.subscriptions.bookNewId(path);

        final var oauthCallbackURI = getSubscription(path).map(WebHookSubscription::getOauthCallbackURI).orElse(null);

        if (oauthCallbackURI == null) {
            idBooking.free();
            throw new IllegalStateException("Cannot perform OAuth : no webhook available");
        }
        return this.createSubscriptionData(idBooking, oAuthListener, oauthCallbackURI);
    }

    private @NonNull OauthSubscriptionData createSubscriptionData(@NonNull IdMap<String, Data>.IdBooking idBooking,
                                                                  @NonNull OAuthListener oAuthListener,
                                                                  @NonNull URI oauthCallbackURI) {
        idBooking.setData(new Data(oAuthListener, instants.now(), oauthCallbackURI));
        final var state = idBooking.getId().getRandom();
        return new OauthSubscriptionData(state, oauthCallbackURI);

    }

    @Synchronized
    private @NonNull Optional<WebHookSubscription> getSubscription(String path) {
        final var sub = subscriptionPerPath.computeIfAbsent(
                path,
                p -> this.webHookManager.addListener(p, this::onCall)
                                        .orElse(null)
        );
        return Optional.ofNullable(sub);
    }

    /**
     * Called when the server send its OAuth result (accepted or denied)
     */
    private void onCall(@NonNull String path,
                        @NonNull RequestMethod method,
                        @NonNull HttpServletRequest request,
                        @NonNull HttpServletResponse response) throws IOException {
        final var state = request.getParameter(STATE_PARAMETER_NAME);
        final var data = getData(new IdKey<>(path, state)).orElse(null);

        if (data != null) {
            data.handleCallback(request, response);
        }
    }


    @RequiredArgsConstructor
    private static class Data {

        private final @NonNull OAuthListener oAuthListener;

        /**
         * The time when the request has been made. Used to perform a timeout
         * on the request
         */
        private final @NonNull Instant timeOfRequest;

        /**
         *
         */
        private final @NonNull URI oauthCallbackURI;

        public void handleCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
            oAuthListener.onCall(oauthCallbackURI, request, response);
        }

        public void timeOut() {
            oAuthListener.onTimeout();
        }

        public void interrupt() {
            oAuthListener.onInterrupted();
        }


        public boolean isTimedOut(@NonNull Instant now) {
            return timeOfRequest.plus(TIMEOUT_DURATION).isBefore(now);
        }

    }


}
