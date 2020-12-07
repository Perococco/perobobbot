package perobobbot.lang;

import lombok.NonNull;

public interface Mutation<S> {

    static <T> Mutation<T> identity() {
        return t -> t;
    }


    @NonNull
    S mutate(@NonNull S state);
}
