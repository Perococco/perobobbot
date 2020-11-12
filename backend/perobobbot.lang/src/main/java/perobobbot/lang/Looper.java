package perobobbot.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;

/**
 * Basically a thread but with start, stop methods.
 * Extends this class and implement the method {@link #performOneIteration()} with the operation
 * that needs to be done in an iteration.
 *
 * The method returns either {@link IterationCommand#CONTINUE} or {@link IterationCommand#STOP} to continue
 * or stop the loop.
 *
 * If an exception occurs in the {@link #performOneIteration()} method, the exception will be logged and
 * the loop will be stopped only if the exception is du to a thread interruption (like {@link InterruptedException}).
 *
 * The methods {@link #beforeLooping()} and {@link #afterLooping()} are called
 * before and after the loop and in the same thread than the loop.
 *
 * If the method {@link #beforeLooping()} throws an exception, the loop is cancelled
 * and the exception will be bubble up to the {@link #start()} method.
 *
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
            ThreadFactories.daemon("Looper %d")
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


    /**
     * Perform on iteration of the loop
     * @return the command for the next iteration
     * @throws Exception if an error occurred
     */
    @NonNull
    protected abstract IterationCommand performOneIteration() throws Exception;


    public boolean isRunning() {
        return lock.get(this::currentIsRunning);
    }

    /**
     * Starts the loop, return only when the loop has been started.
     * If an exception occurred while calling {@link #beforeLooping()}, the loop
     * will not be started, and this method will throw the exception thrown
     * by {@link #beforeLooping()}.
     */
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

    protected void beforeLooping() {}

    protected void afterLooping() {}

    @RequiredArgsConstructor
    private class Loop implements Runnable {

        @NonNull
        private final CompletableFuture<Nil> starting;

        @Override
        public void run() {
            try {
                Thread.currentThread().setName("➰ "+Looper.this.getClass().getSimpleName());
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
                        final boolean isCausedByInterruption = ThrowableTool.isCausedByAnInterruption(e);
                        if (ThrowableTool.isCausedByAnInterruption(e)) {
                            LOG.warn("Loop interrupted");
                            Thread.currentThread().interrupt();
                        } else {
                            LOG.warn("Iteration failed : ", e);
                        }
                    }
                }
            } finally {
                afterLooping();
                Thread.currentThread().setName("Looper idle");
            }
        }
    }

}
