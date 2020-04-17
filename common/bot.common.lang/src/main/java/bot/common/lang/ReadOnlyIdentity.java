package bot.common.lang;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
public interface ReadOnlyIdentity<S> {

    @NonNull
    CompletionStage<?> run(@NonNull Consumer<? super S> action);

    @NonNull
    <R> CompletionStage<R> apply(@NonNull Function<? super S, ? extends R> function);

    void runAndWait(@NonNull Consumer<? super S> action) throws InterruptedException, ExecutionException;

    @NonNull
    <R> R applyAndWait(@NonNull Function<? super S, ? extends R> function) throws InterruptedException, ExecutionException;
}
