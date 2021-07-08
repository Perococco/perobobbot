package perobobbot.twitch.eventsub.api;

import lombok.NonNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public interface EventSubRequestHandler {

    @NonNull Optional<TwitchRequestContent<EventSubRequest>> handleCallback(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull String secret) throws IOException, ServletException;

}
