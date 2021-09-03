package perobobbot.server.sse;

import lombok.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface Hub {

    @NonNull SseEmitter createEmitterForNewConnection();

    @NonNull SseEmitter createEmitterForReconnection(@NonNull String lastEventId);


}
