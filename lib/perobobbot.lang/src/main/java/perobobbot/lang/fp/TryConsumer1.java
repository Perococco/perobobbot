package perobobbot.lang.fp;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.ThrowableTool;

public interface TryConsumer1<A,X extends Throwable> {

    void accept(@NonNull A value) throws X;

    @NonNull
    default TryResult<Throwable, Nil> fSafe(@NonNull A value) {
        try {
            accept(value);
            return TryResult.success(Nil.NIL);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return TryResult.failure(t);
        }
    }


}
