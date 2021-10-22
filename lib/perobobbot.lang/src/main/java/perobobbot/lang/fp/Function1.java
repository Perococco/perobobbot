package perobobbot.lang.fp;

import lombok.NonNull;

import java.util.function.Function;

public interface Function1<A,R> extends Function<A,R> {

    @NonNull
    static <A,R> Function1<A,R> toFunction1(@NonNull Function<A,R> function) {
        if (function instanceof Function1) {
            return (Function1<A,R>)function;
        }
        return function::apply;
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    static <T> Function1<T,T> identity() {
        return t -> t;
    }



    @NonNull
    R f(@NonNull A a);

    @Override
    @NonNull
    default R apply(@NonNull A a) {
        return f(a);
    }

    @NonNull
    default Function0<R> f1(@NonNull A a) {
        return () -> f(a);
    }

    @NonNull
    default Function1<A,Function0<R>> curry() {
        return a -> () -> f(a);
    }

    @NonNull
    default <S> Function1<A,S> then(@NonNull Function1<? super R, ? extends S> after) {
        return a -> after.f(this.f(a));
    }

    @NonNull
    default <S> Consumer1<A> thenAccept(@NonNull Consumer1<? super R> after) {
        return a -> after.f(this.f(a));
    }

    @NonNull
    default <S> Function1<S,R> compose(@NonNull Function1< ? super S, ? extends A> before) {
        return s -> this.apply(before.f(s));
    }
}
