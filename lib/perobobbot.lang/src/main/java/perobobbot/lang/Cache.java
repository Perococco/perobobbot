package perobobbot.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.fp.Function0;
import perobobbot.lang.fp.Function1;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

@RequiredArgsConstructor
public class Cache<V> implements Function0<V> {

    @NonNull
    public static <V> Cache<V> soft(@NonNull Function0<V> valueSupplier) {
        return new Cache<>(SoftReference::new, valueSupplier);
    }

    @NonNull
    public static <V> Cache<V> weak(@NonNull Function0<V> valueSupplier) {
        return new Cache<>(WeakReference::new, valueSupplier);
    }

    @NonNull
    private final Function1<? super V, ? extends Reference<V>> referenceFactory;

    @NonNull
    private final Function0<V> supplier;

    private Reference<V> reference = null;

    @NonNull
    public <U> Cache<U> mapSoft(@NonNull Function1<? super V, ? extends U> mapper) {
        return new Cache<>(SoftReference::new, () -> mapper.f(this.get()) );
    }

    @NonNull
    @Override
    @Synchronized
    public V f() {
        {
            final V cachedValue = reference == null ? null : reference.get();
            if (cachedValue != null) {
                return cachedValue;
            }
        }
        final V newValue = supplier.get();
        this.reference = referenceFactory.f(newValue);
        return newValue;
    }
}
