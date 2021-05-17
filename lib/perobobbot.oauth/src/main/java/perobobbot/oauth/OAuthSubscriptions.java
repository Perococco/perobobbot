package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookManager;
import perobobbot.http.WebHookSubscription;
import perobobbot.lang.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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


    public void removeAll() {
        lock.runLocked(subscriptions::clear);
    }

    @Scheduled(fixedRate = TIME_OUT_CLEAN_UP)
    public void handleTimeOut() {
        final var now = instants.now();
        this.subscriptions.removeIf(d -> d.isTimedOut(now));
    }

    private @NonNull Optional<Data> getData(@NonNull IdKey<String> idKey) {
        return lock.getLocked(() -> this.subscriptions.remove(idKey));
    }

    public @NonNull SubscriptionData subscribe(@NonNull String path, @NonNull OAuthListener oAuthListener) {
        final var idBooking = this.subscriptions.bookNewId(path);
        final var subscription = this.webHookManager.addListener(path, this::onCall);
        final var timeOfRequest = instants.now();

        final Data data = new Data(oAuthListener, timeOfRequest, subscription);

        idBooking.setData(data);

        return new SubscriptionData(idBooking.getId().getRandom(), subscription.getOauthCallbackURI());
    }

    private void onCall(@NonNull String path,
                       @NonNull RequestMethod method,
                       @NonNull HttpServletRequest request,
                       @NonNull HttpServletResponse response) throws IOException {
        final var state = request.getParameter(STATE_PARAMETER_NAME);
        final var data = getData(new IdKey<>(path, state)).orElse(null);

        if (data != null) {
            data.unsubscribe();
            data.handleCallback(request, response);
        }
    }


    @RequiredArgsConstructor
    private static class Data implements Disposable {

        private final OAuthListener oAuthListener;

        private final @NonNull Instant timeOfRequest;

        @Getter
        private final @NonNull WebHookSubscription webHookSubscription;

        public void unsubscribe() {
            this.webHookSubscription.unsubscribe();
        }

        public void handleCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
            oAuthListener.onCall(webHookSubscription.getOauthCallbackURI(), request, response);
        }

        @Override
        public void dispose() {
            oAuthListener.onTimeout();
        }

        public boolean isTimedOut(@NonNull Instant now) {
            return timeOfRequest.plus(TIMEOUT_DURATION).isBefore(now);
        }
    }


}
