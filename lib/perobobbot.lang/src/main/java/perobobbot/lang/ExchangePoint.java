package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Either;

import java.util.concurrent.SynchronousQueue;

public class ExchangePoint<I,O> {

    private final SynchronousQueue<Either<I,O>> queue = new SynchronousQueue<>();

    public @NonNull O pushInputAndWaitOutput(@NonNull I input) throws InterruptedException {
        putInput(input);
        return takeOutput();
    }

    public @NonNull O takeOutput() throws InterruptedException {
        return queue.take().right().orElseThrow(() -> new RuntimeException("Invalid state, an output was expected"));
    }

    public @NonNull I takeInput() throws InterruptedException {
        return queue.take().left().orElseThrow(() -> new RuntimeException("Invalid state, an output was expected"));
    }

    public @NonNull void putOutput(@NonNull O output) throws InterruptedException {
        this.queue.put(Either.right(output));
    }

    public @NonNull void putInput(@NonNull I input) throws InterruptedException {
        this.queue.put(Either.left(input));
    }
}
