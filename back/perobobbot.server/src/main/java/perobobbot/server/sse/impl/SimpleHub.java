package perobobbot.server.sse.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.lang.Parser;
import perobobbot.lang.ParsingFailure;
import perobobbot.lang.ThreadFactories;
import perobobbot.lang.fp.TryResult;
import perobobbot.server.sse.EventBuffer;
import perobobbot.server.sse.Hub;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Log4j2
@RequiredArgsConstructor
public class SimpleHub implements Hub {

    private final ExecutorService EVENT_SENDER_EXECUTOR = Executors.newCachedThreadPool(ThreadFactories.daemon("Event Emitter %d"));

    @Value("${sse-emitter.timeout}")
    private final long timeout;

    private final @NonNull EventBuffer eventBuffer;

    private final Map<SseEmitter, Long> emitters = new ConcurrentHashMap<>();


    @Scheduled(fixedRate = 500)
    public void sendEvents() {
        final long firstId;
        {
            final var minId = findMinimalIdOfSentMessages();
            if (minId.isEmpty()) {
                return;
            }
            firstId = minId.getAsLong() + 1;
        }

        final var emitters = List.copyOf(this.emitters.keySet());
        final var messages = eventBuffer.getAllMessagesFrom(firstId);

        if (!messages.isEmpty()) {
            emitters.forEach(emitter -> EVENT_SENDER_EXECUTOR.submit(() -> doSendEvents(messages, emitter)));
        }
    }

    private OptionalLong findMinimalIdOfSentMessages() {
        return emitters.values()
                       .stream()
                       .mapToLong(l -> l)
                       .min();
    }

    public void doSendEvents(@NonNull ImmutableList<EventBuffer.Event> events, @NonNull SseEmitter emitter) {
        final long lastEventId = emitters.get(emitter);

        try {
            for (EventBuffer.Event event : events) {
                if (event.getId() <= lastEventId) {
                    continue;
                }
                emitter.send(event.getPayload());
            }
            final var lastEventIdSent = events.get(events.size() - 1).getId();
            emitters.put(emitter, lastEventIdSent);
        } catch (IOException e) {
            emitters.remove(emitter);
            emitter.completeWithError(e);
        }
    }


    @Override
    public @NonNull SseEmitter createEmitterForNewConnection() {
        final var smallestId = eventBuffer.getSmallestId();
        return putEmitter(smallestId);
    }

    @Override
    public @NonNull SseEmitter createEmitterForReconnection(@NonNull String lastEventId) {
        return tryParseLastEventId(lastEventId)
                .map(this::putEmitter)
                .get();
    }

    private @NonNull SseEmitter putEmitter(long lastEventId) {
        final var emitter = createEmitter(lastEventId);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitters.put(emitter, lastEventId);
        return emitter;
    }

    private SseEmitter createEmitter(long lastEventId) {
        var emitter = new SseEmitter(this.timeout);
        EVENT_SENDER_EXECUTOR.submit(() -> {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(lastEventId))
                                       .name("message")
                                       .data("connection opened", MediaType.TEXT_PLAIN));
            } catch (Throwable e) {
                e.printStackTrace();
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    private TryResult<ParsingFailure, Long> tryParseLastEventId(@NonNull String lastEventId) {
        return Parser.PARSE_LONG.parse(lastEventId);
    }
}
