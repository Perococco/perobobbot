package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer0;
import perobobbot.lang.fp.Function0;

public interface Transaction {

    long amount();

    void complete();

    void rollback();

    default <T> @NonNull T getAndRollBackOnError(@NonNull Function0<T> action) {
        try {
            final var result = action.f();
            this.complete();
            return result;
        } catch (Throwable t) {
            this.rollback();
            throw t;
        }
    }
    default void runAndRollBackOnError(@NonNull Consumer0 execution) {
        getAndRollBackOnError(execution.asFunction());
    }
}
