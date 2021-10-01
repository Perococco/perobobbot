package perobobbot.server.sse.impl;

import com.google.common.collect.ImmutableList;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.lang.ThreadFactories;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.Todo;
import perobobbot.security.com.BotUser;
import perobobbot.security.com.User;
import perobobbot.server.sse.EventBuffer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultSseEmitterRegistry implements SseEmitterRegistry {

    private final ExecutorService EVENT_SENDER_EXECUTOR = Executors.newCachedThreadPool(ThreadFactories.daemon("Event Emitter %d"));

    public final Map<UUID, EmitterData> emitters = new HashMap<>();

    @Override
    public @NonNull SseEmitter createEmitter(@NonNull User user, long lastEventId, long timeout) {
        var emitterData = register(user, lastEventId, timeout);
        EVENT_SENDER_EXECUTOR.submit(emitterData::sendConnectionMessage);
        return emitterData.getEmitter();
    }

    @Override
    @Synchronized
    public @NonNull OptionalLong findMinimalIdOfSentMessage() {
        return emitters.values()
                       .stream()
                       .mapToLong(EmitterData::getLastSentEventId)
                       .min();
    }

    @Override
    public void send(ImmutableList<EventBuffer.Event> messages) {
        if (messages.isEmpty()) {
            return;
        }
        final var emitterDataList = copyAllEmitterData();
        emitterDataList.forEach(emitterData -> EVENT_SENDER_EXECUTOR.submit(() -> emitterData.send(messages)));
    }


    private @NonNull EmitterData register(@NonNull User user, long lastEventId, long timeout) {
        final var emitterData = new EmitterData(new SseEmitter(timeout), user, lastEventId);
        final var uuid = addToMap(emitterData);

        emitterData.getEmitter().onCompletion(() -> unregister(uuid));
        emitterData.getEmitter().onError(t -> unregister(uuid));
        emitterData.getEmitter().onTimeout(() -> unregister(uuid));

        return emitterData;
    }

    @Synchronized
    private List<EmitterData> copyAllEmitterData() {
        return List.copyOf(emitters.values());
    }

    @Synchronized
    private void unregister(@NonNull UUID uuid) {
        emitters.remove(uuid);
    }

    @Synchronized
    private @NonNull UUID addToMap(@NonNull EmitterData emitterData) {
        while (true) {
            var uuid = UUID.randomUUID();
            if (!emitters.containsKey(uuid)) {
                emitters.put(uuid, emitterData);
                return uuid;
            }
        }
    }


    @Getter
    @AllArgsConstructor
    private static class EmitterData {
        private final @NonNull SseEmitter emitter;
        private final @NonNull User user;
        private long lastSentEventId;


        public void sendConnectionMessage() {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(lastSentEventId))
                                       .name("message")
                                       .data("connection opened", MediaType.TEXT_PLAIN));
            } catch (Throwable e) {
                emitter.completeWithError(e);
            }

        }

        public void send(@NonNull ImmutableList<EventBuffer.Event> events) {
            try {
                for (EventBuffer.Event event : events) {
                    if (shouldSendMessage(event)) {
                        emitter.send(event.getPayload());
                    }
                }
                this.lastSentEventId = events.get(events.size() - 1).getId();
            } catch (IOException e) {
                emitter.completeWithError(e);
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
            }
        }

        private boolean shouldSendMessage(EventBuffer.Event event) {
            if (event.getId() <= lastSentEventId) {
                return false;
            }
            return event.isAllowedToSend(user.getLogin());
        }
    }

}
