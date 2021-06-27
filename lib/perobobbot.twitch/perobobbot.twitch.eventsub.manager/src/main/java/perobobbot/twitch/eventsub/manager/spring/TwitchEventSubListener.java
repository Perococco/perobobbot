package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookListener;
import perobobbot.twitch.eventsub.api.EventSubHandler;
import perobobbot.twitch.eventsub.manager._private.EventSubTwitchRequestTransformer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
@Log4j2
public class TwitchEventSubListener implements WebHookListener {

    private final @NonNull ExecutorService executorService;
    private final @NonNull String secret;
    private final EventSubTwitchRequestTransformer twitchRequestTransformer;

    private final EventSubHandler eventSubHandler;

    public void onCall(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws ServletException, IOException {
        final var result = twitchRequestTransformer.handleCallback(request, response, secret);
        result.ifPresent(r -> System.out.println(r.content()));
        result.ifPresent(r -> executorService.submit(() -> eventSubHandler.handleEventSubRequest(r)));
    }

}
