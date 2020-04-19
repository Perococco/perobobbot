package bot.common.lang.fp;

import bot.common.lang.ThrowableTool;
import lombok.NonNull;

public interface Try1<A,R,X extends Throwable> {

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
