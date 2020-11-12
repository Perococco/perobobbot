package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Lazy;
import perobobbot.lang.fp.Function0;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class ThreadSafeLazy<T> implements Lazy<T> {

    private final AtomicReference<T> value = new AtomicReference<>(null);

    @NonNull
    private final Function0<? extends T> supplier;

    @Override
    public T get() {
        return value.updateAndGet(v -> v != null ? v : supplier.get());
    }
}