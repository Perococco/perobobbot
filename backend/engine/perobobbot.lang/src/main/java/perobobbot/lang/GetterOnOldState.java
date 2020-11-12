package perobobbot.lang;

import lombok.NonNull;

public interface GetterOnOldState<S, T> {

    @NonNull
    T getValue(@NonNull S oldState);

}
