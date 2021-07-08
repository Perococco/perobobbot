package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookListener;
import perobobbot.lang.Handler;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;
import perobobbot.twitch.eventsub.manager._private.EventSubTwitchRequestTransformer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Log4j2
public class TwitchEventSubListener implements WebHookListener {

    private final @NonNull ExecutorService executorService;
    private final @NonNull String secret;
    private final EventSubTwitchRequestTransformer twitchRequestTransformer;
    private final Handler<TwitchRequestContent<EventSubRequest>,?> handler;

    public void onCall(@NonNull String path, @NonNull RequestMethod method,
                       @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws ServletException, IOException {
        final var result = twitchRequestTransformer.handleCallback(request, response, secret).orElse(null);
        if (result == null) {
            return;
        }
        executorService.submit(() -> {
            handler.handle(result);
        });
    }

}
