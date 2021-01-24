package perobobbot.state;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 */
public interface MapStateBase<K, V> {


    /**
     * @return the size of this map
     */
    int size();

    boolean isEmpty();

    /**
     * @param key the key of the value to get
     * @return an optional containing the value if it exists, an empty optional otherwise
     */
    @NonNull
    Optional<V> get(@NonNull K key);

    /**
     * @return the set of the entries of this map
     */
    @NonNull
    ImmutableSet<Map.Entry<K, V>> entrySet();

    /**
     * @return the set of the keys of this map
     */
    @NonNull
    ImmutableSet<K> keySet();

    /**
     * @return the collection of the values of this map
     */
    @NonNull
    ImmutableCollection<V> values();

    boolean containsKey(@NonNull K key);

}
