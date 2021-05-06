package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

import java.util.Optional;

public interface Caster<T> extends Function1<Object,Optional<T>>  {

    @NonNull Optional<T> cast(@NonNull Object object);

    @Override
    @NonNull
    default Optional<T> f(@NonNull Object o) {
        return cast(o);
    }
}
