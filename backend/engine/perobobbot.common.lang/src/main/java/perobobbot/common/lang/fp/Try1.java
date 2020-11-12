package perobobbot.common.lang.fp;

import lombok.NonNull;
import perobobbot.common.lang.ThrowableTool;

public interface Try1<A,R,X extends Throwable> {

    @NonNull
    R f(@NonNull A a) throws X;

    @NonNull
    default TryResult<Throwable,R> fSafe(@NonNull A a) {
        try {
            return TryResult.success(f(a));
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return TryResult.failure(t);
        }
    }

    @NonNull
    default Function1<A,TryResult<Throwable,R>> safe() {
        return this::fSafe;
    }

}
