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
    <T> CompletionStage<T> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull GetterOnStates<? super S, ? extends T> getter);

    @NonNull
    default <T> CompletionStage<T> mutateAndGetFromOldState(@NonNull Mutation<S> mutation, @NonNull GetterOnOldState<? super S, ? extends T> mutatedStateGetter) {
        return mutateAndGet(mutation, (o,n) -> mutatedStateGetter.getValue(o));
    }

    @NonNull
    default CompletionStage<S> mutate(@NonNull Mutation<S> mutation) {
        return mutateAndGet(mutation,(o,n) -> n);
    }

}
