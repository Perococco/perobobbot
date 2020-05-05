package bot.common.lang;

import bot.common.lang.fp.Function1;
import lombok.NonNull;

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
    <T> CompletionStage<MutationResult<S,T>> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter);

    @NonNull
    default CompletionStage<MutationResult<S,S>> mutate(@NonNull Mutation<S> mutation) {
        return mutateAndGet(mutation, s -> s);
    }

}
