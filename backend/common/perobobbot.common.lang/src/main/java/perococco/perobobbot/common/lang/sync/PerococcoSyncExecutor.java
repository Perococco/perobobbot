package perococco.perobobbot.common.lang.sync;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.SyncExecutor;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Function0;

import java.util.concurrent.*;

@RequiredArgsConstructor
public class PerococcoSyncExecutor<I> implements SyncExecutor<I> {

    private final SynExecutorLooper<I> looper;

    public PerococcoSyncExecutor(@NonNull String name) {
        this(Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                                                   .setDaemon(true)
                                                   .setNameFormat(name+" %d")
                                                   .build()));
    }

    public PerococcoSyncExecutor(@NonNull Executor executor) {
        this.looper = new SynExecutorLooper<>(executor);
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
                completableFuture.complete(action.f());
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                completableFuture.completeExceptionally(t);
            }
        };
        looper.addJob(id,runnable);
        return completableFuture;
    }
}
