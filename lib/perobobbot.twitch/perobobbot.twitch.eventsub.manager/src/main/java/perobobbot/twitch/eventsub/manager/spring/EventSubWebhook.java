package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookListener;
import perobobbot.http.WebHookManager;
import perobobbot.http.WebHookSubscription;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.*;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import perobobbot.twitch.eventsub.manager._private.EventSubTwitchRequestTransformer;
import reactor.core.publisher.Mono;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class EventSubWebhook {

    private final @NonNull String eventSubPath;
    private final @NonNull WebHookManager webHookManager;

    @Setter
    private WebHookListener webHookListener;

    private WebHookSubscription webHookSubscription;


    @Synchronized
    public void start() {
        webHookSubscription = this.webHookManager.addListener(eventSubPath, this::onCall).orElse(null);
    }

    @Synchronized
    public void stop() {
        if (webHookSubscription != null) {
            webHookSubscription.unsubscribe();
            webHookSubscription = null;
        }
    }

    public @NonNull Optional<URI> getCallbackURI() {
        return Optional.ofNullable(webHookSubscription).map(WebHookSubscription::getWebHookCallbackURI);
    }

    private void onCall(@NonNull String path,
                        @NonNull RequestMethod requestMethod,
                        @NonNull HttpServletRequest request,
                        @NonNull HttpServletResponse response) throws ServletException, IOException {
        final var listener = this.webHookListener;
        if (listener != null) {
            listener.onCall(path, requestMethod, request, response);
        }
    }


}
