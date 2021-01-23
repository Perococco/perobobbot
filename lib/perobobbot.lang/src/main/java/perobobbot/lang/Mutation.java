package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.common.lang.SimpleOperator;

public interface Mutation<S> {

    static <T> Mutation<T> identity() {
        return t -> t;
    }

    @NonNull
    S mutate(@NonNull S state);

    default @NonNull Mutation<S> then(@NonNull Mutation<S> after) {
        return s -> after.mutate(this.mutate(s));
    }

    default @NonNull <T> Operator<S, T> thenGet(@NonNull GetterOnStates<? super S, ? extends T> action) {
        return new SimpleOperator<>(this, action);
    }

    default @NonNull Operator<S,S> asOperator() {
        return new SimpleOperator<>(this,GetterOnStates.newStateGetter());
    }
}
