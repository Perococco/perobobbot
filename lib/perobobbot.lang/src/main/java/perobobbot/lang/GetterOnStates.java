package perobobbot.lang;

import lombok.NonNull;

public interface GetterOnStates<S, T> {

    @NonNull
    T getValue(@NonNull S oldState, @NonNull S newState);


    static @NonNull<S> GetterOnStates<S,S> newStateGetter() {
        return (oldState, newState) -> newState;
    }


}
