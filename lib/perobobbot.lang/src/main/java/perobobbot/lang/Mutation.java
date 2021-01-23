package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

public interface Mutation<S> {

    static <T> Mutation<T> identity() {
        return t -> t;
    }

    @NonNull
    S mutate(@NonNull S state);

    default @NonNull Mutation<S> then(@NonNull Mutation<S> after) {
        return s -> after.mutate(this.mutate(s));
    }

    default @NonNull <T> Operator<S,T> thenGet(@NonNull Function1<? super S, ? extends T> action) {
        return Operator.mutatorGetter(this,action);
    }
}
