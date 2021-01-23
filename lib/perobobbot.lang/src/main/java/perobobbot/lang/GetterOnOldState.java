package perobobbot.lang;

import lombok.NonNull;

public interface GetterOnOldState<S, T> extends GetterOnStates<S,T> {

    @NonNull
    T getValue(@NonNull S oldState);

    @Override
    @NonNull
    default T getValue(@NonNull S oldState, @NonNull S newState) {
        return getValue(oldState);
    }
}
