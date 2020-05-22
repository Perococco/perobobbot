package perobobbot.common.lang.fp;

import lombok.NonNull;
import perobobbot.common.lang.Factory;
import perobobbot.common.lang.Nil;

public interface Consumer0 extends Runnable {

    @NonNull
    static Consumer0 toConsumer0(@NonNull Runnable runnable) {
        return runnable instanceof Consumer0 ? ((Consumer0) runnable) : runnable::run;
    }

    void f();

    @Override
    default void run() {
        f();
    }

    @NonNull
    default Function0<Nil> asFunction() {
        return () -> {
            this.f();
            return Nil.NIL;
        };
    }
}
