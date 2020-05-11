package perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class ImmutableEntry<K,V> implements Map.Entry<K,V> {

    public static <K,V> ImmutableEntry<K,V> of(@NonNull K key, @NonNull V value) {
        return new ImmutableEntry<>(key,value);
    }

    @NonNull
    private final K key;

    @NonNull
    private final V value;

    @Override
    public @NonNull K getKey() {
        return key;
    }

    @Override
    public @NonNull V getValue() {
        return value;
    }

    @Override
    @Deprecated
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}
