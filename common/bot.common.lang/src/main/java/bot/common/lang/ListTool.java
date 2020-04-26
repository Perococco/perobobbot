package bot.common.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

/**
 * @author perococco
 **/
public class ListTool {

    @NonNull
    public static <A> ImmutableList<A> addFirst(@NonNull ImmutableList<A> source, @NonNull A element) {
        return ImmutableList.<A>builder()
                .add(element)
                .addAll(source)
                .build();
    }

    @NonNull
    public static <A> ImmutableList<A> addLast(@NonNull ImmutableList<A> source, @NonNull A element) {
        return ImmutableList.<A>builder()
                .addAll(source)
                .add(element)
                .build();
    }

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
}
