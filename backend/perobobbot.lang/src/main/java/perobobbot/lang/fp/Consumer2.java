package perobobbot.lang.fp;

import lombok.NonNull;

import java.util.function.BiConsumer;

public interface Consumer2<A,B> extends BiConsumer<A,B> {

    @NonNull
    static <A,B> Consumer2<A,B> toConsumer2(@NonNull BiConsumer<A,B> consumer) {
        return consumer instanceof Consumer2 ? ((Consumer2<A,B>) consumer) : consumer::accept;
    }

    @NonNull
    static <A,B> Consumer2<A,B> of(@NonNull Consumer2<A,B> consumer) {
        return consumer;
    }


    void f(@NonNull A a, @NonNull B b);

    @NonNull
    default Function1<A,Function1<B,Consumer0>> curry() {
        return a -> b -> () -> f(a,b);
    }

    @NonNull
    default Consumer1<B> f1(@NonNull A a) {
        return b -> f(a,b);
    }

    @NonNull
    default Consumer1<A> f2(@NonNull B b) {
        return a -> f(a,b);
    }

    @Override
    default void accept(@NonNull A a, @NonNull B b) {
        f(a,b);
    }
}
