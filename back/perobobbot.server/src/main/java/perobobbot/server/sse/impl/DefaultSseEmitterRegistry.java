package perobobbot.server.sse.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.lang.MapTool;
import perobobbot.lang.ThreadFactories;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.Todo;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.security.com.BotUser;
import perobobbot.security.com.User;
import perobobbot.server.sse.EventBuffer;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class DefaultSseEmitterRegistry implements SseEmitterRegistry {


    private final ExecutorService EVENT_SENDER_EXECUTOR = Executors.newCachedThreadPool(ThreadFactories.daemon("Event Emitter %d"));

    public @NonNull ImmutableMap<UUID, EmitterData> emitters = ImmutableMap.of();

    @Override
    public @NonNull SseEmitter createEmitter(@NonNull User user, long lastEventId, long timeout) {
        var emitterData = register(user, lastEventId, timeout);
        EVENT_SENDER_EXECUTOR.submit(emitterData::sendConnectionMessage);
        return emitterData.getEmitter();
    }

    @Override
    public @NonNull OptionalLong findMinimalIdOfSentMessage() {
        return emitters.values()
                       .stream()
                       .mapToLong(EmitterData::getLastSentEventId)
                       .min();
    }

    @Override
    public void send(ImmutableList<EventBuffer.Event> messages) {
        final var emitterDataList = emitters.values();
        emitterDataList.forEach(emitterData -> EVENT_SENDER_EXECUTOR.submit(() -> emitterData.send(messages)));
    }


    private @NonNull EmitterData register(@NonNull User user, long lastEventId, long timeout) {
        return addToMap(uuid -> new EmitterData(uuid,timeout,user,lastEventId));
    }

    @Synchronized
    private void unregister(@NonNull UUID uuid) {
        updateEmitters(e -> MapTool.remove(e,uuid));
    }

    @Synchronized
    private @NonNull EmitterData addToMap(@NonNull Function1<UUID,EmitterData> emitterDataFactory) {
        while (true) {
            var uuid = UUID.randomUUID();
            if (!emitters.containsKey(uuid)) {
                final var emitterData = emitterDataFactory.f(uuid);
                updateEmitters(e -> MapTool.add(e,uuid,emitterData));
                return emitterData;
            }
        }
    }

    private void updateEmitters(@NonNull UnaryOperator1<ImmutableMap<UUID,EmitterData>> mutation) {
        final int nbEmitters = emitters.size();
        this.emitters = mutation.apply(emitters);
        final int newNbEmitters = emitters.size();
        LOG.info("SseEmitters count : {} -> {}",nbEmitters,newNbEmitters);
    }


    @Getter
    private class EmitterData {

        private static final long MAX_DURATION_WITHOUT_MESSAGES = 30_000;

        private final @NonNull UUID id;
        private final @NonNull SseEmitter emitter;
        private final @NonNull User user;
        private long lastSentEventId;
        private Instant timeOfLastMessageSent;


        public EmitterData(@NonNull UUID id,
                           long timeout,
                           @NonNull User user, long lastSentEventId) {
            this.id = id;
            this.emitter = new SseEmitter(timeout);
            this.user = user;
            this.lastSentEventId = lastSentEventId;
            this.timeOfLastMessageSent = Instant.now();
        }

        public void sendConnectionMessage() {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(lastSentEventId))
                                       .name("message")
                                       .data("connection opened", MediaType.TEXT_PLAIN));
            } catch (Throwable e) {
                emitter.completeWithError(e);
                unregister(id);
            }

        }

        public void send(@NonNull ImmutableList<EventBuffer.Event> events) {
            final var now = Instant.now();
            boolean someMessageSent = false;
            try {
                for (EventBuffer.Event event : events) {
                    if (shouldSendMessage(event)) {
                        someMessageSent = true;
                        emitter.send(event.getPayload());
                    }
                }
                final var shouldPing = !someMessageSent && Duration.between(timeOfLastMessageSent,now).toMillis() > MAX_DURATION_WITHOUT_MESSAGES;
                if (shouldPing){
                        emitter.send(pingEvent(now));
                }

                if (!events.isEmpty()) {
                    lastSentEventId = events.get(events.size() - 1).getId();
                }

                if (someMessageSent || shouldPing) {
                    timeOfLastMessageSent = now;
                }

            } catch (Throwable e) {
                emitter.completeWithError(e);
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
            }
        }

        private SseEmitter.SseEventBuilder pingEvent(@NonNull Instant now) {
            return SseEmitter.event()
                             .id(String.valueOf(lastSentEventId))
                             .name("ping")
                             .data(now.toString(), MediaType.TEXT_PLAIN);
        }

        private boolean shouldSendMessage(EventBuffer.Event event) {
            if (event.getId() <= lastSentEventId) {
                return false;
            }
            return event.isAllowedToSend(user.getLogin());
        }
    }

}
