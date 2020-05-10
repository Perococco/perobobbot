package bot.common.lang;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;

/**
 * @author perococco
 **/
@Log4j2
public abstract class Looper {

    @NonNull
    public static Looper basic(@NonNull Callable<IterationCommand> iterationAction) {
        return new Looper() {
            @Override
            protected @NonNull IterationCommand performOneIteration() throws Exception {
                return iterationAction.call();
            }
        };
    }


    public enum IterationCommand {
        CONTINUE,
        STOP
    }

    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("Looper %d")
                    .build()
    );

    private final ExecutorService executorService;

    private final SmartLock lock = SmartLock.reentrant();
    private final Condition done = lock.newCondition();

    private Future<?> current = null;

    public Looper() {
        this(DEFAULT_EXECUTOR_SERVICE);
    }

    public Looper(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public boolean isRunning() {
        return lock.get(this::currentIsRunning);
    }

    public void start() {
        final CompletableFuture<Nil> starting = new CompletableFuture<>();
        lock.runLocked(() -> {
            if (current != null) {
                requestStop();
            }
            this.current = executorService.submit(new Loop(starting));
        });
        try {
            starting.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException)e.getCause();
            }
            throw new RuntimeException("Expected only runtime exception",e.getCause());
        }
    }

    public void requestStop() {
        lock.runLocked(() -> this.current.cancel(true));
    }

    public void waitForCompletion() throws InterruptedException {
        lock.call(() -> {
            while (currentIsRunning()) {
                done.await();
            }
            return Nil.NIL;
        });
    }

    private boolean currentIsRunning() {
        return current != null && !current.isDone();
    }

    protected void beforeLooping() {};

    @NonNull
    protected abstract IterationCommand performOneIteration() throws Exception;

    protected void afterLooping() {};

    @RequiredArgsConstructor
    private class Loop implements Runnable {

        @NonNull
        private final CompletableFuture<Nil> starting;

        @Override
        public void run() {
            try {
                beforeLooping();
                starting.complete(Nil.NIL);
            } catch (RuntimeException e) {
                starting.completeExceptionally(e);
                return;
            }

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        final IterationCommand command = performOneIteration();
                        if (command == IterationCommand.STOP) {
                            break;
                        }
                    } catch (Exception e) {
                        ThrowableTool.interruptThreadIfCausedByInterruption(e);
                        LOG.warn("Iteration failed : ", e);
                    }
                }
            } finally {
                afterLooping();
            }
        }
    }

}
