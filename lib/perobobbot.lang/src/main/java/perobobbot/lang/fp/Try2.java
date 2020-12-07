package perobobbot.lang.fp;

import lombok.NonNull;
import perobobbot.lang.ThrowableTool;

public interface Try2<A,B,R,X extends Throwable> {

    @NonNull
    R f(@NonNull A a,B b) throws X;

    @NonNull
    default TryResult<Throwable,R> fSafe(@NonNull A a, @NonNull B b) {
        try {
            return TryResult.success(f(a,b));
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return TryResult.failure(t);
        }
    }

    @NonNull
    default Function2<A,B,TryResult<Throwable,R>> safe() {
        return this::fSafe;
    }

}
