package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Streams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ImmutableBag<T> implements Bag<T> {

    private final ImmutableMap<T,Integer> content;

    public static <T> ImmutableBag<T> empty() {
        return new ImmutableBag<>(ImmutableMap.of());
    }


    @Override
    public @NonNull ImmutableBag<T> toImmutable() {
        return this;
    }

    @Override
    public int count(@NonNull T item) {
        return content.getOrDefault(item,0);
    }

    @Override
    @Deprecated
    public void update(@NonNull T item, IntUnaryOperator updater) {
        throw new IllegalStateException("This bag is immutable");
    }

    @Override
    @Deprecated
    public void clear() {
        throw new IllegalStateException("This bag is immutable");
    }

    @Override
    @Deprecated
    public void addOne(@NonNull T item) {
        throw new IllegalStateException("This bag is immutable");
    }

    @Override
    @Deprecated
    public void add(@NonNull T item, int count) {
        throw new IllegalStateException("This bag is immutable");
    }

    @Override
    public @NonNull Iterator<CountedValue<T>> iterator() {
        return Iterators.transform(content.entrySet().iterator(), e -> new CountedValue<>(e.getKey(), e.getValue()));
    }

    @Override
    public @NonNull Stream<CountedValue<T>> stream() {
        return Streams.stream(this);
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public @NonNull Set<T> KeySet() {
        return content.keySet();
    }
}
