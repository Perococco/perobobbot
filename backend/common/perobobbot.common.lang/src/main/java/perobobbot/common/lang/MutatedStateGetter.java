package perobobbot.common.lang;

import lombok.NonNull;

public interface MutatedStateGetter<S, T> {

    @NonNull
    T getValue(@NonNull S oldState, @NonNull S newState);

}
