package perobobbot.lang;

import lombok.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.IdentityHashMap;

/**
 * @author perococco
 **/
public class Disposer  {

    @NonNull
    private final IdentityHashMap<Reference<?>,Runnable> referenceMap = new IdentityHashMap<>();

    private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

    public Disposer() {
        Looper.basic(this::consumerReferenceQueue).start();
    }

    @NonNull
    public <T> WeakReference<T> add(@NonNull T value, @NonNull Runnable actionOnGC) {
        final WeakReference<T> reference = new WeakReference<>(value, referenceQueue);
        referenceMap.put(reference,actionOnGC);
        return reference;
    }

    @NonNull
    private Looper.IterationCommand consumerReferenceQueue() throws InterruptedException {
        final Reference<?> reference = referenceQueue.remove();
        final Runnable runnable = referenceMap.remove(reference);
        if (runnable != null) {
            runnable.run();
        }
        return Looper.IterationCommand.CONTINUE;
    }

}
