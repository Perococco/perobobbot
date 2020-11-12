package perobobbot.lang.fp;

import lombok.NonNull;
import perobobbot.lang.Nil;

import java.util.function.Consumer;

public interface Consumer1<A> extends Consumer<A> {

    @NonNull
    static <A> Consumer1<A> toConsumer1(@NonNull Consumer<A> consumer) {
        return consumer instanceof Consumer1 ? ((Consumer1<A>) consumer) : consumer::accept;
    }


    void f(@NonNull A a);

    @NonNull
    default Function1<A, Nil> toFunction() {
        return a -> { accept(a);return Nil.NIL;};
    }

    @NonNull
    default Function1<A,Consumer0> curry() {
        return a -> () -> f(a);
    }

    @Override
    default void accept(@NonNull A a) {
        f(a);
    }
}
