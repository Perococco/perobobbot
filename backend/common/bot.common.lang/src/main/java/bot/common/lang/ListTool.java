package bot.common.lang;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * @author perococco
 **/
public class ListTool {

    /**
     * @return a list made by concatenating <code>element</code> and <code>source</code>
     */
    @NonNull
    public static <A> ImmutableList<A> addFirst(@NonNull ImmutableList<A> source, @NonNull A element) {
        return ImmutableList.<A>builder()
                .add(element)
                .addAll(source)
                .build();
    }

    /**
     * @return a list made by concatenating <code>source</code> and <code>element</code>
     */
    @NonNull
    public static <A> ImmutableList<A> addLast(@NonNull ImmutableList<A> source, @NonNull A element) {
        return ImmutableList.<A>builder()
                .addAll(source)
                .add(element)
                .build();
    }

    /**
     * @return a list with first occurrence of the <code>element</code> removed from the <code>source</code>
     */
    @NonNull
    public static <A> ImmutableList<A> removeFirst(@NonNull ImmutableList<A> source, @NonNull A element) {
        boolean removed = false;
        final ImmutableList.Builder<A> builder = ImmutableList.builder();
        for (A a : source) {
            if (removed || !a.equals(element)) {
                builder.add(element);
            } else {
                removed = true;
            }
        }
        return removed?builder.build():source;
    }

    /**
     * @return a list with last occurrence of the <code>element</code> removed from the <code>source</code>
     */
    @NonNull
    public static <A> ImmutableList<A> removeLast(@NonNull ImmutableList<A> source, @NonNull A element) {
        boolean removed = false;
        final ImmutableList.Builder<A> builder = ImmutableList.builder();
        for (A a : source.reverse()) {
            if (removed || !a.equals(element)) {
                builder.add(element);
            } else {
                removed = true;
            }
        }
        return removed?builder.build().reverse():source;
    }

    @NonNull
    public static <A> Optional<IndexedValue<A>> findFirst(@NonNull ImmutableList<A> list, @NonNull Predicate<? super A> predicate) {
        int index = 0;
        for (A a : list) {
            if (predicate.test(a)) {
                return Optional.of(IndexedValue.create(index, a));
            }
            index++;
        }
        return Optional.empty();
    }

    @NonNull
    public static <A> ImmutableList<A> replace(@NonNull ImmutableList<A> source, @NonNull IndexedValue<? extends A> indexedValue) {
        return multiReplace(source, indexedValue.map(Arrays::asList));
    }

    @NonNull
    public static <A> ImmutableList<A> multiReplace(@NonNull ImmutableList<A> source, @NonNull IndexedValue<? extends Iterable<? extends A>> indexedValue) {
        final ImmutableList.Builder<A> builder = ImmutableList.builder();
        for (int i = 0; i < source.size(); i++) {
            if (i == indexedValue.getIndex()) {
                builder.addAll(indexedValue.getValue());
            } else {
                builder.add(source.get(i));
            }
        }
        return builder.build();
    }
}