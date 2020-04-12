package bot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.IdentityHashMap;
import java.util.function.Consumer;

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
    public <T> T add(@NonNull T value, @NonNull Consumer<? super T> actionOnGC) {
        final WeakReference<T> reference = new WeakReference<>(value,referenceQueue);
        referenceMap.put(reference,new SoftAction<>(reference,actionOnGC));
        return value;
    }

    @NonNull
    public <T> T add(@NonNull T value, @NonNull Runnable actionOnGC) {
        final PhantomReference<T> reference = new PhantomReference<>(value, referenceQueue);
        referenceMap.put(reference,actionOnGC);
        return value;
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

    @RequiredArgsConstructor
    private static class SoftAction<T> implements Runnable {

        @NonNull
        private final WeakReference<T> reference;

        @NonNull
        private final Consumer<? super T> action;

        @Override
        public void run() {
            action.accept(reference.get());
        }
    }

}
