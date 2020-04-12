package bot.common.lang;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;
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
}
