package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.common.lang.SimpleOperator;

public interface Operator<S,T> {

    @NonNull S mutate(S state);

    @NonNull T apply(@NonNull S state);

    static <S,T> @NonNull Operator<S,T> getter(@NonNull Function1<? super S, ? extends T> action) {
        return new SimpleOperator<>(s -> s, action);
    }

    static <S> @NonNull Operator<S,S> mutator(@NonNull Mutation<S> mutation) {
        return new SimpleOperator<>(mutation, s -> s);
    }

    static <S,T> @NonNull Operator<S,T> mutatorGetter(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends T> action) {
        return new SimpleOperator<>(mutation, action);
    }

}
