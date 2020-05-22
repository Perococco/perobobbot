package perococco.perobobbot.common.lang.sync;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Looper;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

@RequiredArgsConstructor
public class SynExecutorLooper<I> extends Looper {

    @NonNull
    @Getter
    private final Executor executor;

    @NonNull
    private final BlockingDeque<Runnable> actionQueue = new LinkedBlockingDeque<>();

    private final Map<I, Deque<Runnable>> runnableById = new HashMap<>();

    private final Set<I> runningId = new HashSet<>();

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        actionQueue.takeFirst().run();
        return IterationCommand.CONTINUE;
    }

    public void addJob(@NonNull I id, @NonNull Runnable action) {
        this.actionQueue.addLast(() -> {
            this.addRunnable(id,action);
            this.ifPossibleExecuteForId(id);
        });
    }

    private boolean isRunning(@NonNull I id) {
        return runnableById.containsKey(id);
    }

    public void ifPossibleExecuteForId(@NonNull I id) {
        if (isRunning(id)) {
            return;
        }
        getNextRunnable(id).ifPresent(r -> submitToExecutor(id,r));
    }

    private void submitToExecutor(@NonNull I id, @NonNull Runnable runnable) {
        this.runningId.add(id);
        executor.execute(() -> {
            try {
                runnable.run();
            } finally {
                actionQueue.addLast(() -> {
                    runnableById.remove(id);
                    ifPossibleExecuteForId(id);
                });
            }
        });
    }

    @NonNull
    private Optional<Runnable> getNextRunnable(@NonNull I id) {
        final Deque<Runnable> runnables = runnableById.get(id);
        if (runnables == null || runnables.isEmpty()) {
            runnableById.remove(id);
            return Optional.empty();
        }
        final Runnable result = runnables.removeFirst();
        if (runnables.isEmpty()) {
            runnableById.remove(id);
        }
        return Optional.of(result);
    }

    public void addRunnable(@NonNull I id, @NonNull Runnable action) {
        this.runnableById.computeIfAbsent(id,i -> new LinkedList<>()).add(action);
    }


}
