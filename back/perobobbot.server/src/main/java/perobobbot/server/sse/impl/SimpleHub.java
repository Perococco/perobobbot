package perobobbot.server.sse.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.lang.Parser;
import perobobbot.lang.ParsingFailure;
import perobobbot.lang.fp.TryResult;
import perobobbot.security.com.User;
import perobobbot.server.sse.EventBuffer;
import perobobbot.server.sse.Hub;

import java.util.OptionalLong;

@Component
@Log4j2
@RequiredArgsConstructor
public class SimpleHub implements Hub {

    @Value("${sse-emitter.timeout}")
    private final long timeout;

    private final @NonNull EventBuffer eventBuffer;

    private final SseEmitterRegistry emitters = new DefaultSseEmitterRegistry();


    @Scheduled(fixedRate = 500)
    public void sendEvents() {
        final OptionalLong firstId = emitters.findMinimalIdOfSentMessage();
        if (firstId.isEmpty()) {
            return;
        }
        final var messages = eventBuffer.getAllMessagesFrom(firstId.getAsLong() + 1);
        emitters.send(messages);
    }

    @Override
    public @NonNull SseEmitter createEmitterForNewConnection(@NonNull User user) {
        return this.createEmitter(user, eventBuffer.getSmallestId());
    }

    @Override
    public @NonNull SseEmitter createEmitterForReconnection(@NonNull User user, @NonNull String lastEventId) {
        return tryParseLastEventId(lastEventId)
                .map(id -> createEmitter(user, id))
                .get();
    }

    private @NonNull SseEmitter createEmitter(@NonNull User user, long id) {
        return emitters.createEmitter(user, id, this.timeout);
    }


    private TryResult<ParsingFailure, Long> tryParseLastEventId(@NonNull String lastEventId) {
        return Parser.PARSE_LONG.parse(lastEventId);
    }
}
