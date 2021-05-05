package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookListener;
import perobobbot.http.WebHookObservable;
import perobobbot.http.WebHookSubscription;
import perobobbot.lang.Instants;
import perobobbot.lang.SmartLock;
import perobobbot.lang.fp.Function1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class OAuthSubscriptions {

    public static final long TIME_OUT_CLEAN_UP = 120_000;

    public static final String STATE_PARAMETER_NAME = "state";

    private static final Duration TIMEOUT_DURATION = Duration.ofMinutes(Integer.getInteger("oauth.user.time-out", 5));

    private final @NonNull Instants instants;
    private final @NonNull WebHookObservable webHookObservable;

    private final SmartLock lock = SmartLock.reentrant();
    private final @NonNull Map<String, Data> subscriptions = new HashMap<>();


    public void removeAll() {
        lock.runLocked(() -> {
            this.subscriptions.values().forEach(Data::unsubscribe);
            this.subscriptions.clear();
        });
    }

    @Scheduled(fixedRate = TIME_OUT_CLEAN_UP)
    public void handleTimeOut() {
        final var now = instants.now();
        final var data = ImmutableList.copyOf(this.subscriptions.values());
        data.forEach(v -> v.handleTimeOut(now));
    }

    public boolean getAndUnsubscribe(@NonNull String state) {
        final var webhookData = lock.getLocked(() -> Optional.ofNullable(this.subscriptions.remove(state)));
        webhookData.ifPresent(Data::unsubscribe);
        return webhookData.isPresent();
    }

    public @NonNull SubscriptionData subscribe(@NonNull String path, @NonNull OAuthListener oAuthListener) {
        return addSubscription(state -> {
            final var timeOfRequest = instants.now();

            final var webHookListener = new ListenerWrapper(state, oAuthListener);
            final var subscription = this.webHookObservable.addListener(path, webHookListener);
            webHookListener.setRedirectURI(subscription.getOauthCallbackURI());

            return new Data(state, oAuthListener, timeOfRequest, subscription);
        });
    }

    private @NonNull SubscriptionData addSubscription(@NonNull Function1<String, Data> dataFactory) {
        while (true) {
            final var state = UUID.randomUUID().toString();

            final Optional<Data> s = lock.getLocked(() -> {
                if (subscriptions.containsKey(state)) {
                    return Optional.empty();
                } else {
                    final var data = dataFactory.apply(state);
                    subscriptions.put(state, data);
                    return Optional.of(data);
                }
            });
            if (s.isPresent()) {
                return s.get();
            }
        }
    }


    @RequiredArgsConstructor
    private class ListenerWrapper implements WebHookListener {

        private final @NonNull String state;

        private final @NonNull OAuthListener oAuthListener;

        private URI redirectURI;

        @Override
        public void onCall(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
            assert redirectURI != null : "Fail to set redirect URI before callback has been call";
            final var state = request.getParameter(STATE_PARAMETER_NAME);
            if (this.state.equals(state)) {
                if (getAndUnsubscribe(state)) {
                    oAuthListener.onCall(redirectURI, request, response);
                }
            }
        }

        private void setRedirectURI(@NonNull URI redirectURI) {
            this.redirectURI = redirectURI;
        }

    }

    @RequiredArgsConstructor
    private class Data implements SubscriptionData {

        @Getter
        private final String state;

        private final OAuthListener oAuthListener;

        private final @NonNull Instant timeOfRequest;

        @Getter
        private final @NonNull WebHookSubscription webHookSubscription;

        @Override
        public @NonNull URI getOAuthRedirectURI() {
            return webHookSubscription.getOauthCallbackURI();
        }

        public void unsubscribe() {
            this.webHookSubscription.unsubscribe();
        }

        public void handleTimeOut(@NonNull Instant now) {
            final var timedOut = timeOfRequest.plus(TIMEOUT_DURATION).isBefore(now);
            if (timedOut) {
                if (getAndUnsubscribe(state)) {
                    oAuthListener.onTimeout(state);
                }
            }
        }
    }


}
