package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.lang.ThrowableTool;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author perococco
 */
@RequiredArgsConstructor
public class DefaultUpdater<S> implements Updater<S> {

    @NonNull
    private final BlockingDeque<UpdateInformation<S,?>> updateInformationQueue = new LinkedBlockingDeque<>();

    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();

    private Thread runningThread = null;


    @Override
    public void start() {
        runLocked(() -> {
            this.stop();
            runningThread = new Thread(new Runner(),"Updater Thread");;
            runningThread.setDaemon(true);
            runningThread.start();
        });
    }

    @Override
    public void stop() {
        runLocked(() -> {
            if (runningThread != null) {
                runningThread.interrupt();
            }
            boolean interrupted = false;
            while (runningThread != null) {
                try {
                    done.await();
                } catch (InterruptedException e) {
                    //Interruption request has been received. We must wait for
                    //the underlying thread to finish.
                    interrupted = true;
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void runLocked(@NonNull Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public @NonNull <R> CompletionStage<UpdateResult<S, R>> offerUpdate(@NonNull Update<S,R> update) {
        final UpdateInformation<S,R> updateInformation = new UpdateInformation<>(update, new CompletableFuture<>());
        runLocked(() -> {
            if (isRunning()) {
                try {
                    this.updateInformationQueue.addLast(updateInformation);
                } catch (Throwable t) {
                    ThrowableTool.interruptThreadIfCausedByInterruption(t);
                    updateInformation.completeExceptionally(new InterruptedException());
                }
            } else {
                updateInformation.completeExceptionally(new InterruptedException());
            }
        });
        return updateInformation.completableFuture;
    }

    private boolean isRunning() {
        return runningThread != null;
    }

    private class Runner implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final UpdateInformation<S,?> updateInformation = updateInformationQueue.takeFirst();
                    updateInformation.performUpdate();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            lock.lock();
            try {
                runningThread = null;
                final Throwable error = new InterruptedException();
                updateInformationQueue.forEach(i -> i.completableFuture.completeExceptionally(error));
                updateInformationQueue.clear();
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    @Value
    private static class UpdateInformation<S, R> {

        @NonNull
        Update<S, R> update;

        @NonNull
        CompletableFuture<UpdateResult<S, R>> completableFuture;

        public void completeExceptionally(@NonNull Throwable error) {
            completableFuture.completeExceptionally(error);
        }

        public void performUpdate() {
            try {
                final UpdateResult<S, R> result = update.performMutation();
                completableFuture.complete(result);
            } catch (Throwable t) {
                t.printStackTrace();
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                completeExceptionally(t);
            }
        }
    }

}
