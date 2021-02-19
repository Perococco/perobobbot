package perobobbot.action;

import lombok.NonNull;
import perobobbot.lang.ThrowableTool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public abstract class AsyncAction<P> implements Action<P,CompletionStage<?>> {

    abstract protected @NonNull CompletionStage<?> executeAsync(@NonNull P parameter);

    @Override
    public CompletionStage<?> execute(@NonNull P parameter) throws Throwable {
        try {
            return executeAsync(parameter);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return CompletableFuture.failedFuture(t);
        }
    }
}
