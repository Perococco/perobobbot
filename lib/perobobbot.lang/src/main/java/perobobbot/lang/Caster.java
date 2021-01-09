package perobobbot.lang;

import lombok.NonNull;

import java.util.Optional;

public interface Caster<T> {

    @NonNull Optional<T> cast(@NonNull Object object);
}
