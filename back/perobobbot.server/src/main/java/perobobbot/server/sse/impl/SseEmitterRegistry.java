package perobobbot.server.sse.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.security.com.User;
import perobobbot.server.sse.EventBuffer;

import java.util.OptionalLong;

public interface SseEmitterRegistry {

    @NonNull OptionalLong findMinimalIdOfSentMessage();

    void send(ImmutableList<EventBuffer.Event> messages);

    @NonNull SseEmitter createEmitter(@NonNull User user, long lastEventId, long timeout);
}
