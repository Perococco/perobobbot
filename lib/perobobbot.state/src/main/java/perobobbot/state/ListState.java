package perobobbot.state;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ListTool;
import perobobbot.lang.fp.Function1;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "content")
public class ListState<V> implements Iterable<V> {

    private static final ListState EMPTY = new ListState<>(ImmutableList.of());

    @SuppressWarnings("unchecked")
    public static <V> ListState<V> empty() {
        return EMPTY;
    }

    @NonNull
    public static <V> ListState<V> of(@NonNull ImmutableList<V> content) {
        return new ListState<>(content);
    }

    @NonNull
    private final ImmutableList<V> content;


    public int size() {
        return content.size();
    }

    @NonNull
    public V get(int index) {
        return content.get(index);
    }


    @NonNull
    public <U> ListState<U> map(@NonNull Function1<? super V, ? extends U> mapper) {
        return new ListState<>(ListTool.map(content, mapper));
    }

    @NonNull
    public ListState<V> replace(@NonNull Predicate<? super V> filter,@NonNull V newValue) {
        return map(v -> filter.test(v)?newValue:v);
    }

    @NonNull
    public ListState<V> replace(@NonNull Predicate<? super V> filter,@NonNull Function<? super V, ? extends V> mapper) {
        if (content.stream().noneMatch(filter)) {
            return this;
        }
        return new ListState<>(ListTool.map(content, v -> filter.test(v)?mapper.apply(v):v));
    }


    @NonNull
    public OptionalInt indexOf(@NonNull Object object) {
        return indexOf(object::equals);
    }

    @NonNull
    public OptionalInt indexOf(@NonNull Predicate<? super V> test) {
        return IntStream.range(0, size()).filter(i -> test.test(content.get(i))).findFirst();
    }

    @NonNull
    public Optional<V> find(int index) {
        if (index < 0 || index >= size()) {
            return Optional.empty();
        }
        return Optional.of(get(index));
    }

    @NonNull
    public Optional<V> find(@NonNull Predicate<? super V> test) {
        return content.stream().filter(test).findFirst();
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
    public ListState<V> removeIf(@NonNull Predicate<? super V> test) {
        return new ListState<>(content.stream().filter(test.negate()).collect(ImmutableList.toImmutableList()));
    }

    @NonNull
    public ListState<V> remove(@NonNull Object value) {
        return removeIf(value::equals);
    }

    public ListState<V> add(@NonNull V value) {
        return toBuilder().add(value).build();
    }


    public static <V> Builder<V> builder() {
        return new Builder<>(empty());
    }

    public Builder<V> toBuilder() {
        return new Builder<>(this);
    }

    public static class Builder<V> {

        @NonNull
        private final List<V> content;

        public Builder(@NonNull ListState<V> listState) {
            this.content = new ArrayList<>(listState.content);
        }

        public Builder<V> add(@NonNull V value) {
            content.add(value);
            return this;
        }

        public Builder<V> add(int index, @NonNull V value) {
            content.add(index, value);
            return this;
        }

        public Builder<V> remove(@NonNull V value) {
            content.remove(value);
            return this;
        }

        public Builder<V> addAll(@NonNull Collection<? extends V> values) {
            content.addAll(values);
            return this;
        }
        public Builder<V> addAll(int index, @NonNull Collection<? extends V> values) {
            content.addAll(index, values);
            return this;
        }

        public ListState<V> build() {
            return new ListState<>(ImmutableList.copyOf(content));
        }
    }
}
