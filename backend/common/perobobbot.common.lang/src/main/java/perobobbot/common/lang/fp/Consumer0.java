package perobobbot.common.lang.fp;

import lombok.NonNull;

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
}
