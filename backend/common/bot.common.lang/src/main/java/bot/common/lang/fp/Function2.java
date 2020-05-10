package bot.common.lang.fp;

import lombok.NonNull;

import java.util.function.BiFunction;

public interface Function2<A,B,R> extends BiFunction<A,B,R> {

    @NonNull
    static <A,B,R> Function2<A,B,R> toFunction2(@NonNull BiFunction<A,B,R> function) {
        if (function instanceof Function2) {
            return (Function2<A,B,R>)function;
        }
        return function::apply;
    }

    @NonNull
    R f(@NonNull A a,@NonNull B b);

    @Override
    @NonNull
    default R apply(@NonNull A a, @NonNull B b) {
        return f(a,b);
    }

    @NonNull
    default Function1<B,R> f1(@NonNull A a) {
        return b -> f(a,b);
    }

    @NonNull
    default Function1<A,R> f2(@NonNull B b) {
        return a -> f(a,b);
    }

    @NonNull
    default Function1<A,Function1<B,Function0<R>>> curry() {
        return a -> b -> () -> f(a,b);
    }

    @NonNull
    default Function1<A,Function1<B,R>> curry1() {
        return a -> b -> f(a,b);
    }

    @NonNull
    default Function1<B,Function1<A,R>> curry2() {
        return b -> a -> f(a,b);
    }

    @NonNull
    default <S> Function2<A,B,S> then(@NonNull Function1<? super R, ? extends S> after) {
        return (a,b) -> after.f(this.f(a,b));
    }
}
