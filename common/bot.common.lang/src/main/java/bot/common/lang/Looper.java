package bot.common.lang;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;

/**
 * @author perococco
 **/
@Log4j2
public abstract class Looper {

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
        lock.runLocked(() -> {
            if (current != null) {
                requestStop();
            }
            this.current = executorService.submit(new Loop());
        });
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

    protected abstract void beforeLooping();

    @NonNull
    protected abstract IterationCommand performOneIteration() throws Exception;

    protected abstract void afterLooping();

    private class Loop implements Runnable {

        @Override
        public void run() {
            beforeLooping();
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
