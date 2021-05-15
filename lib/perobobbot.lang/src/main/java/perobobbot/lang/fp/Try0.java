package perobobbot.lang.fp;

import lombok.NonNull;
import perobobbot.lang.ThrowableTool;

public interface Try0<R,X extends Throwable> {

    @NonNull
    R f() throws X;

    @NonNull
    default TryResult<Throwable,R> fSafe() {
        try {
            return TryResult.success(f());
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return TryResult.failure(t);
        }
    }


}
