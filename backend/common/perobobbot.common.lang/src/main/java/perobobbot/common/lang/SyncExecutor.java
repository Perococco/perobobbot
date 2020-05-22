package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Consumer0;
import perobobbot.common.lang.fp.Function0;
import perococco.perobobbot.common.lang.sync.PerococcoSyncExecutor;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * Execute submitted action in a pool thread but ensure
 * that action with the same id are executed sequentially
 * @param <I> the type of the id used to identify the actions
 */
public interface SyncExecutor<I> {

    @NonNull
    static <I> SyncExecutor<I> create(@NonNull Executor executor) {
        return new PerococcoSyncExecutor<I>(executor);
    }

    @NonNull
    static <I> SyncExecutor<I> create(@NonNull String name) {
        return new PerococcoSyncExecutor<I>(name);
    }

    void start();

    void requestStop();

    void waitForCompletion() throws InterruptedException;

    @NonNull
    <R> CompletionStage<R> submit(@NonNull I id, @NonNull Function0<R> action);

    @NonNull
    default CompletionStage<Nil> submit(@NonNull I id, @NonNull Consumer0 action) {
        return submit(id, action.asFunction());
    }


}
