package perococco.perobobbot.frontfx.action;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.frontfx.action.ActionCancelled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class ActionItem<P,R> {

    @NonNull
    @Getter
    private final Action<? super P, ? extends R> action;

    @NonNull
    @Getter
    private final P parameter;

    @NonNull
    private final CompletableFuture<R> completableFuture;

    private final AtomicBoolean completed = new AtomicBoolean(false);

    public void cancel() {
        executeIfNotCompleted(() -> completeExceptionally(new ActionCancelled(action.getClass())));
    }

    public void executeIfNotCompleted(Runnable runnable) {
        if (!completed.getAndSet(true)) {
            runnable.run();
        }
    }

    public void completeWith(@NonNull R result) {
        completed.set(true);
        this.completableFuture.complete(result);
    }

    public void completeExceptionally(Throwable t) {
        completed.set(true);
        this.completableFuture.completeExceptionally(t);
    }

}
