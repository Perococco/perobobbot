package perobobbot.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function0;
import perobobbot.lang.fp.Function1;

import java.util.Set;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class Exec<T> {


    private static final Exec<Nil> NIL = new Exec<>(Nil.NIL);

    public static @NonNull <T> Exec<T> with(@NonNull T value) {
        return new Exec<>(value);
    }

    private final @NonNull T value;

    public @NonNull Exec<T> checkNotIn(@NonNull Set<T> keys, Function1<? super T, ? extends RuntimeException> exceptionProvider) {
        return checkIsNot(keys::contains,exceptionProvider);
    }

    public @NonNull Exec<T> checkIsNot(@NonNull Predicate<? super T> test, Function1<? super T, ? extends RuntimeException> exceptionProvider) {
        if (test.test(value)) {
            throw exceptionProvider.f(value);
        }
        return this;
    }

    public @NonNull Nil exec(@NonNull Consumer1<? super T> consumer) {
        consumer.accept(value);
        return Nil.NIL;
    }

    public @NonNull <A> A apply(@NonNull Function1<? super T, ? extends A> action) {
        return action.f(value);
    }

    public @NonNull <A> Exec<A> map(@NonNull Function1<? super T, ? extends A> action) {
        return with(action.f(value));
    }
}
