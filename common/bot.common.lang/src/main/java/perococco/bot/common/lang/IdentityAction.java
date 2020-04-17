package perococco.bot.common.lang;

import bot.common.lang.Nil;
import bot.common.lang.ThrowableTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class IdentityAction<S,R> {

    @NonNull
    public static <S> IdentityAction<S,S> mutation(@NonNull Function< ? super S, ? extends S> mutation) {
        return new IdentityAction<>(mutation,(s,r)->s);
    }

    @NonNull
    public static <S> IdentityAction<S,Nil> run(@NonNull Consumer< ? super S> action) {
        return new IdentityAction<>(s -> {action.accept(s);return Nil.NIL;},(s,r)->s);
    }

    @NonNull
    public static <S,R> IdentityAction<S,R> apply(@NonNull Function< ? super S, ? extends R> function) {
        return new IdentityAction<>(function, (s,r)->s);
    }

    @NonNull
    private final CompletableFuture<R> completableFuture = new CompletableFuture<>();

    @NonNull
    private final Function<? super S, ? extends R> actionOnState;

    @NonNull
    private final BiFunction<? super S, ? super R, ? extends S> finalizer;

    @NonNull
    public CompletionStage<R> completionStage() {
        return completableFuture;
    }

    public R get() throws ExecutionException, InterruptedException {
        return completableFuture.get();
    }

    public S execute(S oldValue) {
        try {
            final R result = actionOnState.apply(oldValue);
            final S newValue = finalizer.apply(oldValue,result);
            completableFuture.complete(result);
            return newValue;
        } catch (Throwable e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            completableFuture.completeExceptionally(e);
            throw e;
        }
    }

    public void stopped() {

    }

    public void notRunning() {

    }

}
