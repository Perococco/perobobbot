package perobobbot.server.sse;

import lombok.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.security.com.BotUser;

public interface Hub {

    /**
     * @param user the user that request to SSE connection
     * @return an sse emitter to transmit the event to the user
     */
    @NonNull SseEmitter createEmitterForNewConnection(@NonNull BotUser user);

    /**
     * @param user the user that request to SSE connection
     * @param lastEventId id of the last received event
     * @return an sse emitter to transmit the event to the user
     */
    @NonNull SseEmitter createEmitterForReconnection(@NonNull BotUser user, @NonNull String lastEventId);


}
