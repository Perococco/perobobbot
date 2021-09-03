package perobobbot.server.sse;

import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.util.List;

public interface EventBuffer {

    void addMessage(@NonNull SSEvent event);

    long getSmallestId();

    @NonNull List<Event> getAllMessagesFrom(long firstId);

    void cleanBuffer();

    @lombok.Value
    class Event {
        long id;
        @NonNull Instant timestamp;
        @NonNull SseEmitter.SseEventBuilder payload;

    }


}
