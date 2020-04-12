package perococco.bot.common.lang;

import bot.common.lang.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
public class PerococcoIdentity<S> implements Identity<S> {

    @NonNull
    public static IdentityFactory provider() {
        return new PerococcoIdentityFactory();
    }

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("Identity Thread %d")
                    .build()
    );

    private final Listeners<IdentityListener<S>> listeners = new Listeners<>();

    @NonNull
    private S value;

    private Future<?> action = null;

    @NonNull
    private final AtomicReference<Consumer<IdentityAction<S,?>>> actionConsumer = new AtomicReference<>(IdentityAction::notRunning);

    public PerococcoIdentity(@NonNull S initialValue) {
        this.value = initialValue;
    }

    void start() {
        action = EXECUTOR_SERVICE.submit(new Runner());
    }
    void stop() {
        action.cancel(true);
        action = null;
    }

    @Override
    public @NonNull Subscription addListener(@NonNull IdentityListener<S> listener) {
        return listeners.addListener(listener);
    }

    @Override
    public @NonNull CompletionStage<S> mutate(@NonNull Function<? super S,? extends S> mutation) {
        return addAction(IdentityAction.mutation(mutation));
    }

    @Override
    public @NonNull CompletionStage<Nil> run(@NonNull Consumer<? super S> action) {
        return addAction(IdentityAction.run(action));
    }

    @Override
    public @NonNull <R> CompletionStage<R> apply(@NonNull Function<? super S,? extends R> function) {
        return addAction(IdentityAction.apply(function));
    }

    @NonNull
    private <R> CompletionStage<R> addAction(@NonNull IdentityAction<S,R> identityAction) {
        actionConsumer.get().accept(identityAction);
        return identityAction.completionStage();
    }

    private class Runner implements Runnable {

        @NonNull
        private final BlockingDeque<IdentityAction<S,?>> actionQueue = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            actionConsumer.set(actionQueue::addLast);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final IdentityAction<S,?> action = actionQueue.take();
                    performAction(action);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            this.switchActionConsumerToStopped();
            this.warnAllRemainingThatWeStopped();
        }

        private void switchActionConsumerToStopped() {
            actionConsumer.set(IdentityAction::stopped);
        }

        private void warnAllRemainingThatWeStopped() {
            final List<IdentityAction<S,?>> remaining = new ArrayList<>(actionQueue.size()+10);
            actionQueue.drainTo(remaining);
            remaining.forEach(IdentityAction::stopped);
        }

        private void performAction(IdentityAction<S,?> action) {
            final S oldValue = value;
            final S newValue = action.execute(oldValue);
            if (newValue == oldValue) {
                return;
            }
            listeners.warnListeners(l -> l.onValueChange(newValue,oldValue));
            value = newValue;
        }
    }
}
