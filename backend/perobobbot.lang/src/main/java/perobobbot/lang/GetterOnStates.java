package perobobbot.lang;

import lombok.NonNull;

public interface GetterOnStates<S, T> {

    @NonNull
    T getValue(@NonNull S oldState, @NonNull S newState);

}
