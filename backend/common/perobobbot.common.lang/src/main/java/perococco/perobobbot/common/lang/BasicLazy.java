package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Lazy;
import perobobbot.common.lang.fp.Function0;

@RequiredArgsConstructor
public class BasicLazy<T> implements Lazy<T> {

    private T value = null;
    @NonNull
    private final Function0<? extends T> supplier;

    @Override
    public T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }
}