package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface AsyncIdentity<S> extends ReadOnlyAsyncIdentity<S> {

    @NonNull
    static <S> AsyncIdentity<S> create(@NonNull S initialValue) {
        return AsyncIdentityFactory.getInstance().create(initialValue);
    }

    @NonNull
    default <T> CompletionStage<T> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter) {
        return mutateAndGet(mutation, (o,n) -> getter.f(n));
    }

    @NonNull
    <T> CompletionStage<T> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull MutatedStateGetter<? super S, ? extends T> mutatedStateGetter);

    @NonNull
    default CompletionStage<S> mutate(@NonNull Mutation<S> mutation) {
        return mutateAndGet(mutation, s -> s);
    }

}
