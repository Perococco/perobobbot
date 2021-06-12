package perobobbot.lang.fp;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.ThrowableTool;

public interface TryConsumer2<A,B,X extends Throwable> {

    void accept(@NonNull A value1, @NonNull B value2) throws X;

    @NonNull
    default TryResult<Throwable, Nil> fSafe(@NonNull A value1, @NonNull B value2) {
        try {
            accept(value1,value2);
            return TryResult.success(Nil.NIL);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return TryResult.failure(t);
        }
    }


}
