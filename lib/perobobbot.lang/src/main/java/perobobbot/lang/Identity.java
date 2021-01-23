package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
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
    <T> T operate(@NonNull Operator<S,T> operator);

    @NonNull
    default <T> T get(@NonNull Function1<? super S, ? extends T> getter) {
        return operate(Operator.getter(getter));
    }

    @NonNull
    default S mutate(@NonNull Mutation<S> mutation) {
        return operate(mutation.asOperator());
    }

}
