package bot.common.lang;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author perococco
 **/
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SetTool {

    @NonNull
    public static <S> UnaryOperator<ImmutableSet<S>> adder(@NonNull S elementToAdd) {
        return s -> add(s,elementToAdd);
    }

    @NonNull
    public static <S> UnaryOperator<ImmutableSet<S>> remover(@NonNull S elementToRemove) {
        return s -> remove(s,elementToRemove);
    }

    @NonNull
    public static <S> ImmutableSet<S> add(@NonNull ImmutableSet<S> source, @NonNull S elementToAdd) {
        if (source.contains(elementToAdd)) {
            return source;
        }
        return ImmutableSet.<S>builder()
                .addAll(source)
                .add(elementToAdd)
                .build();
    }

    @NonNull
    public static <S> ImmutableSet<S> remove(@NonNull ImmutableSet<S> source, @NonNull S elementToRemove) {
        if (!source.contains(elementToRemove)) {
            return source;
        }
        return source.stream().filter(e -> !e.equals(elementToRemove)).collect(ImmutableSet.toImmutableSet());
    }

    @NonNull
    public static <V,S> ImmutableSet<S> map(@NonNull Collection<V> values, @NonNull Function<? super V, ? extends S> mapper) {
        return values.stream()
                     .map(mapper)
                     .distinct()
                     .collect(ImmutableSet.toImmutableSet());
    }

    @NonNull
    public static <V,S> ImmutableSet<S> mapOptionally(@NonNull Collection<V> values, @NonNull Function<? super V, ? extends Optional<? extends S>> mapper) {
        return values.stream()
                     .map(mapper)
                     .flatMap(Optional::stream)
                     .distinct()
                     .collect(ImmutableSet.toImmutableSet());
    }
}
