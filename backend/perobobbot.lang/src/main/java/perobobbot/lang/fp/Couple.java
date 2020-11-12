package perobobbot.lang.fp;

import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

@Value
public class Couple<T> implements Iterable<T> {

    @NonNull
    public static <T> Couple<T> of( @NonNull T first, @NonNull T second) {
        return new Couple<>(first,second);
    }


    @NonNull
    private final T first;

    @NonNull
    private final T second;

    @NonNull
    public Stream<T> stream() {
        return Stream.of(first,second);
    }

    @Override
    public @NonNull Iterator<T> iterator() {
        return Arrays.asList(first,second).iterator();
    }
}
