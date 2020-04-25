package bot.common.lang;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

/**
 * @author perococco
 **/
public class MapTool {

    @NonNull
    public static <K,V> Collector<V,?,ImmutableMap<K,V>> collector(@NonNull Function<? super V, ? extends K> keyGetter) {
        return ImmutableMap.toImmutableMap(keyGetter, v->v);
    }

    @NonNull
    public static <K,V> Collector<Map.Entry<K,V>,?,ImmutableMap<K,V>> entryCollector() {
        return ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    @NonNull
    public static <K,V> UnaryOperator<ImmutableMap<K,V>> adder(@NonNull K keyToAdd, @NonNull V valueToAdd) {
        return s -> add(s,keyToAdd,valueToAdd);
    }

    @NonNull
    public static <K,V> UnaryOperator<ImmutableMap<K,V>> remover(@NonNull K keyToRemove) {
        return s -> remove(s,keyToRemove);
    }

    @NonNull
    public static <K,V>  ImmutableMap<K,V> remove(@NonNull ImmutableMap<K,V> source, @NonNull K keyToRemove) {
        if (!source.containsKey(keyToRemove)) {
            return source;
        }
        return source.entrySet()
              .stream()
              .filter(e -> !e.getKey().equals(keyToRemove))
              .collect(entryCollector());
    }


    @NonNull
    public static <K,V> ImmutableMap<K,V> add(@NonNull ImmutableMap<K,V> source, @NonNull K keyToAdd, @NonNull V valueToAdd) {
        if (source.isEmpty()) {
            return ImmutableMap.of(keyToAdd,valueToAdd);
        }
        final V existingValue = source.get(keyToAdd);

        if (existingValue == null) {
            return ImmutableMap.<K,V>builder()
                    .putAll(source)
                    .put(keyToAdd,valueToAdd)
                    .build();
        }
        else if (valueToAdd.equals(existingValue)) {
            return source;
        }
        else {
            final ImmutableMap.Builder<K,V> builder = ImmutableMap.builder();
            for (Map.Entry<K, V> entry : source.entrySet()) {
                final K key = entry.getKey();
                final V value = key.equals(keyToAdd)?valueToAdd:entry.getValue();

                builder.put(key,value);
            }

            return builder.build();
        }
    }

    @NonNull
    public static <S> ImmutableSet<S> remove(@NonNull ImmutableSet<S> source, @NonNull S elementToRemove) {
        if (!source.contains(elementToRemove)) {
            return source;
        }
        return source.stream().filter(e -> !e.equals(elementToRemove)).collect(ImmutableSet.toImmutableSet());
    }

}
