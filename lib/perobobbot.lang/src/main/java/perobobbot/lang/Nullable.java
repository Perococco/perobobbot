package perobobbot.lang;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.function.Supplier;

public class Nullable<T> {

    private volatile T value;

    public @NonNull Optional<T> get() {
        return Optional.ofNullable(value);
    }

    public void set(@NonNull T value) {
        this.value = value;
    }

    public void clear() {
        this.value = null;
    }

    public @NonNull <U> Optional<U> map(@NonNull Function1<? super T, ? extends U> mapper) {
        return Optional.ofNullable(value).map(mapper);
    }

    public @NonNull <U> Optional<U> flatMap(@NonNull Function1<? super T, ? extends Optional<? extends U>> mapper) {
        return Optional.ofNullable(value).flatMap(mapper);
    }

    public <X extends Throwable> @NonNull T orElseThrow(@NonNull Supplier<? extends X> errorSupplier) throws X{
        return get().orElseThrow(errorSupplier);
    }

    public void ifPresent(@NonNull Consumer1<? super T> action) {
        final var value = this.value;
        if (value != null) {
            action.accept(value);
        }
    }
}
