package perobobbot.lang.fp;

import lombok.NonNull;

import java.util.function.BiConsumer;

public interface Consumer3<A,B,C> {

    @NonNull
    static <A,B,C> Consumer3<A,B,C> of(@NonNull Consumer3<A,B,C> consumer) {
        return consumer;
    }

    void f(@NonNull A a, @NonNull B b, @NonNull C c);

}
