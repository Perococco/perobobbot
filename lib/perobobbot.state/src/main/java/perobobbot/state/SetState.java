package perobobbot.state;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "content")
public class SetState<V> implements Iterable<V> {

    private static final SetState EMPTY = new SetState<>(ImmutableSet.of());

    @SuppressWarnings("unchecked")
    public static <V> SetState<V> empty() {
        return EMPTY;
    }

    @NonNull
    @Getter
    private final ImmutableSet<V> content;

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean contains(Object value) {
        return content.contains(value);
    }

    public int size() {
        return content.size();
    }

    @NonNull
    @Override
    public Iterator<V> iterator() {
        return content.iterator();
    }

    @NonNull
    public Stream<V> stream() {
        return content.stream();
    }

    @NonNull
    public SetState<V> removeIf(@NonNull Predicate<? super V> test) {
        return new SetState<>(content.stream().filter(test.negate()).collect(ImmutableSet.toImmutableSet()));
    }

    @NonNull
    public SetState<V> remove(@NonNull V value) {
        if (content.contains(value)) {
            return removeIf(value::equals);
        }
        return this;
    }

    @NonNull
    public SetState<V> add(@NonNull V value) {
        return toBuilder().add(value).build();
    }

    public Builder<V> toBuilder() {
        return new Builder<>(this);
    }

    public static <V> Builder<V> builder() {
        return new Builder<>(empty());
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    @RequiredArgsConstructor
    public static class Builder<V> {

        @NonNull
        private final SetState<V> reference;

        @NonNull
        private final Set<Object> removedValues = new HashSet<>();

        @NonNull
        private final Set<V> addedValues = new HashSet<>();

        public Builder<V> addAll(@NonNull ImmutableCollection<V> values) {
            addedValues.addAll(values);
            removedValues.removeAll(values);
            return this;
        }

        public Builder<V> add(@NonNull V value) {
            addedValues.add(value);
            removedValues.remove(value);
            return this;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        public Builder<V> remove(@NonNull Object value) {
            addedValues.remove(value);
            removedValues.add(value);
            return this;
        }

        public Builder<V> removeAll(@NonNull ImmutableCollection<V> values) {
            addedValues.removeAll(values);
            removedValues.addAll(values);
            return this;
        }

        public SetState<V> build() {
            final ImmutableSet<V> content = Stream.concat(
                    reference.content.stream().filter(this::isNotRemovedNorAdded),
                    addedValues.stream()
            ).collect(ImmutableSet.toImmutableSet());
            return new SetState<>(content);
        }

        private boolean isNotRemovedNorAdded(V value) {
            return !removedValues.contains(value) && !addedValues.contains(value);
        }

    }


}
