package perococco.perobobbot.common.lang.sync;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import perobobbot.common.lang.SyncExecutor;
import perobobbot.common.lang.ThreadFactories;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Function0;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Log4j2
public class PerococcoSyncExecutor<I> implements SyncExecutor<I> {

    private static final Marker MARKER = MarkerManager.getMarker("EXECUTOR");

    private final SyncExecutorLooper<I> looper;

    public PerococcoSyncExecutor(@NonNull String name) {
        this(Executors.newCachedThreadPool(ThreadFactories.daemon(name+" %d")));
    }

    public PerococcoSyncExecutor(@NonNull Executor executor) {
        this.looper = new SyncExecutorLooper<>(executor);
        this.looper.start();
    }

    @Override
    public void start() {
        looper.start();
    }

    @Override
    public void requestStop() {
        looper.requestStop();
    }

    @Override
    public void waitForCompletion() throws InterruptedException {
        looper.waitForCompletion();
    }

    @Override
    public @NonNull <R> CompletionStage<R> submit(@NonNull I id, @NonNull Function0<R> action) {
        final CompletableFuture<R> completableFuture = new CompletableFuture<>();
        final Runnable runnable = () -> {
            try {
                LOG.debug(MARKER,"Action '{}' : START",id);
                completableFuture.complete(action.f());
                LOG.debug(MARKER,"Action '{}' : DONE",id);
            } catch (Throwable t) {
                LOG.warn(MARKER,"Action '{}' : FAILED",id,t);
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                completableFuture.completeExceptionally(t);
            }
        };
        looper.addJob(id,runnable);
        return completableFuture;
    }
}
