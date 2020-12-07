package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function1;

@RequiredArgsConstructor
@Getter
public class IdentityOperator<T> {

    private final @NonNull IdentityMutator mutator;

    private final @NonNull Function1<? super ConnectionState, ? extends T> getter;

    public @NonNull ConnectionState mutate(@NonNull ConnectionState value) {
        return mutator.mutate(value);
    }

    public @NonNull T get(@NonNull ConnectionState value) {
        return getter.f(value);
    }
}
