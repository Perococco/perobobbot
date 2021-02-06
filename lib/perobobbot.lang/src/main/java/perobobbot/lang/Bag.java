package perobobbot.lang;

import lombok.NonNull;

import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

public interface Bag<T> extends Iterable<CountedValue<T>>{

    @NonNull ImmutableBag<T> toImmutable();

    int count(@NonNull T item);

    void update(@NonNull T item, IntUnaryOperator updater);

    default void addOne(@NonNull T item) {
        update(item, i -> i+1);
    }

    default void add(@NonNull T item, int count) {
        update(item, i -> i+count);
    }

    @NonNull Stream<CountedValue<T>> stream();

    void clear();

    int size();

    @NonNull Set<T> keySet();
}
