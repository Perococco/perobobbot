package perobobbot.common.lang;

import perobobbot.common.lang.fp.Function1;
import lombok.NonNull;
import perococco.perobobbot.common.lang.PerococcoIdentity;


/**
 * @author perococco
 **/
public interface Identity<S> extends ReadOnlyIdentity<S> {

    @NonNull
    static <S> Identity<S> create(@NonNull S initialState) {
        return new PerococcoIdentity<>(initialState);
    }

    @NonNull
    <T> T mutateAndGet(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> getter);

    @NonNull
    default S mutate(@NonNull Mutation<S> mutation) {
        return mutateAndGet(mutation, s -> s);
    }

}
