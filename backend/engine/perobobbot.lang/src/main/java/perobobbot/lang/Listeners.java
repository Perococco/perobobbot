package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Consumer2;

/**
 * @author perococco
 **/
@Log4j2
public class Listeners<L> {

    private ImmutableList<L> listeners = ImmutableList.of();


    public <P> void warnListeners(@NonNull Consumer2<? super L, ? super P> action, @NonNull P parameter) {
        warnListeners(l -> action.accept(l,parameter));
    }

    public void warnListeners(@NonNull Consumer1<? super L> action) {
        listeners.forEach(l -> {
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
        this.listeners = ListTool.addLast(listeners,listener);
        return () -> removeListener(listener);
    }

    @Synchronized
    private void removeListener(@NonNull L listener) {
        this.listeners = ListTool.removeLast(listeners,listener);
    }
}
