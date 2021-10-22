package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Consumer2;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author perococco
 **/
@Log4j2
public class Listeners<L> {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private ImmutableMap<Long,L> listeners = ImmutableMap.of();


    public <P> void warnListeners(@NonNull Consumer2<? super L, ? super P> action, @NonNull P parameter) {
        warnListeners(l -> action.accept(l,parameter));
    }

    public void warnListeners(@NonNull Consumer1<? super L> action) {
        listeners.values().forEach(l -> {
            try {
                action.accept(l);
            } catch (Exception t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                LOG.warn(() -> String.format("Error while calling listener '%s'",l),t);
            }
        });
    }

    @Synchronized
    public Subscription addListener(@NonNull L listener) {
        final var id = ID_GENERATOR.incrementAndGet();
        final var builder = ImmutableMap.<Long,L>builder();
        builder.putAll(listeners);
        builder.put(id,listener);
        listeners = builder.build();
        return () -> removeListener(id);
    }

    @Synchronized
    private void removeListener(long id) {
        this.listeners = MapTool.remove(listeners, id);
    }
}
