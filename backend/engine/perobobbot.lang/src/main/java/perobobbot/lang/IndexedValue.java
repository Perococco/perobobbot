package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function1;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexedValue<T> {

    @NonNull
    public static <T> IndexedValue<T> create(int index, @NonNull T value) {
        return new IndexedValue<>(index,value);
    }
    @Getter
    private final int index;

    @NonNull
    @Getter
    private final T value;

    @NonNull
    public <R> IndexedValue<R> map(@NonNull Function1<? super T, ? extends R> mutator) {
        return new IndexedValue<>(index,mutator.apply(value));
    }

    @NonNull
    public <R> Optional<IndexedValue<R>> tryMap(@NonNull Function1<? super T, ? extends Optional<? extends R>> mutator) {
        return mutator.apply(value).map(v ->  new IndexedValue<>(index,v));
    }

    @NonNull
    public ImmutableList<T> replaceIn(@NonNull ImmutableList<T> source) {
        return ListTool.replace(source,this);
    }
}
