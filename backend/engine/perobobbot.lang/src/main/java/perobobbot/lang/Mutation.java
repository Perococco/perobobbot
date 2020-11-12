package perobobbot.lang;

import lombok.NonNull;

public interface Mutation<S> {

    @NonNull
    S mutate(@NonNull S state);
}
