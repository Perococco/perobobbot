package perobobbot.common.lang.fp;

import lombok.NonNull;

public interface Function3<A,B,C,R> {

    @NonNull
    R f(@NonNull A a,@NonNull B b, @NonNull C c);

    @NonNull
    default Function2<B,C,R> f1(@NonNull A a) {
        return (b,c) -> f(a,b,c);
    }

    @NonNull
    default Function2<A,C,R> f2(@NonNull B b) {
        return (a,c) -> f(a,b,c);
    }

    @NonNull
    default Function2<A,B,R> f3(@NonNull C c) {
        return (a,b) -> f(a,b,c);
    }

    @NonNull
    default Function1<A,Function1<B,Function1<C,Function0<R>>>> curry() {
        return a -> b -> c -> () -> f(a,b,c);
    }

    @NonNull
    default Function1<A,Function2<B,C,R>> curry1() {
        return a -> (b,c) -> f(a,b,c);
    }

    @NonNull
    default Function1<B,Function2<A,C,R>> curry2() {
        return b -> (a,c) -> f(a,b,c);
    }

    @NonNull
    default <S> Function3<A,B,C,S> then(@NonNull Function1<? super R, ? extends S> after) {
        return (a,b,c) -> after.f(this.f(a,b,c));
    }
}
