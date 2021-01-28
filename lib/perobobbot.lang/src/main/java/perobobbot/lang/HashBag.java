package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Streams;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

public class HashBag<T> implements Bag<T> {

    private final Map<T,Integer> content = new HashMap<>();

    @Override
    public @NonNull ImmutableBag<T> toImmutable() {
        return new ImmutableBag<>(ImmutableMap.copyOf(content));
    }

    @Override
    public int count(@NonNull T item) {
        return content.getOrDefault(item,0);
    }

    @Override
    public void update(@NonNull T item, IntUnaryOperator updater) {
        final int newValue = updater.applyAsInt(count(item));
        if (newValue == 0) {
            content.remove(item);
        }
        else {
            content.put(item,newValue);
        }
    }

    @Override
    public @NonNull Iterator<CountedValue<T>> iterator() {
        return Iterators.transform(content.entrySet().iterator(), e -> new CountedValue<>(e.getKey(),e.getValue()));
    }

    @Override
    public @NonNull Stream<CountedValue<T>> stream() {
        return Streams.stream(this);
    }

    @Override
    public void clear() {
        this.content.clear();
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public @NonNull Set<T> keySet() {
        return content.keySet();
    }
}
