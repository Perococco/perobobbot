package perobobbot.server.sse.impl;

import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.lang.Instants;
import perobobbot.lang.Looper;
import perobobbot.lang.SmartLock;
import perobobbot.server.sse.EventBuffer;
import perobobbot.server.sse.SSEvent;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

@Component
@Log4j2
public class SimpleEventBuffer implements EventBuffer {

    private static final long FIRST_ID = 1;

    private final @NonNull Instants instants;

    private final long bufferDurationInMs;

    private final AtomicLong id = new AtomicLong(FIRST_ID);

    private final Deque<EventBuffer.Event> messages = new LinkedList<>();

    private final Loop loop = new Loop();

    private final SmartLock lock = SmartLock.reentrant();

    public SimpleEventBuffer(@NonNull Instants instants, @Value("${event-history.buffer-duration}") long bufferDurationInMs) {
        this.instants = instants;
        this.bufferDurationInMs = bufferDurationInMs;
        this.loop.start();
    }

    @PreDestroy
    public void dispose() {
        this.loop.requestStop();
    }

    private EventBuffer.Event createMessage(SSEvent sseEvent) {
        final var id = this.id.getAndIncrement();
        final var timestamp = instants.now();
        final var event = SseEmitter.event()
                                    .id(String.valueOf(id))
                                    .name(sseEvent.getEventName())
                                    .data(sseEvent.getPayload());

        return new EventBuffer.Event(id, timestamp, event);
    }

    @Override
    public long getSmallestId() {
        final var first = messages.peekFirst();
        return first == null ? FIRST_ID : first.getId();
    }


    @Override
    public void addMessage(@NonNull SSEvent sseEvent) {
        loop.addAction(msgList -> {
            final var event = createMessage(sseEvent);
            msgList.addLast(event);
        });
    }

    @Override
    @Scheduled(fixedRate = 10_000)
    public void cleanBuffer() {
        loop.addAction(msgList -> {
            final var toOldThreshold = instants.now().minus(Duration.ofMillis(bufferDurationInMs));
            msgList.removeIf(m -> m.getTimestamp().isBefore(toOldThreshold));
        });
    }

    @Override
    public @NonNull List<EventBuffer.Event> getAllMessagesFrom(long firstId) {
        return lock.getLocked(() -> messages.stream().filter(m -> m.getId() >= firstId).toList());
    }


    private class Loop extends Looper {

        private final BlockingDeque<Consumer<? super Deque<Event>>> actions = new LinkedBlockingDeque<>();

        @Synchronized
        public void addAction(@NonNull Consumer<? super Deque<Event>> action) {
            this.actions.add(action);
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            final var action = actions.take();


            lock.runLocked(() -> action.accept(messages));


            return IterationCommand.CONTINUE;
        }
    }

}
