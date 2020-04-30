package bot.common.lang;

import bot.common.lang.fp.Function1;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
public interface Identity<S> extends ReadOnlyIdentity<S> {

    @NonNull
    static <S> Identity<S> create(@NonNull S initialValue) {
        return IdentityFactory.getInstance().create(initialValue);
    }

    @NonNull
    <T> CompletionStage<MutationResult<S,T>> mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter);

    @NonNull
    default CompletionStage<MutationResult<S,S>> mutate(@NonNull Mutation<S> mutation) {
        return mutateAndGet(mutation, s -> s);
    }

}
