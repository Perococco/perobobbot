package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Mutation;
import perobobbot.lang.Operator;
import perobobbot.lang.fp.Function1;

@RequiredArgsConstructor
public class SimpleOperator<S,T> implements Operator<S,T> {

    private final @NonNull Mutation<S> mutation;

    private final @NonNull Function1<? super S, ? extends T> action;

    public @NonNull S mutate(S state) {
        return mutation.mutate(state);
    }

    public @NonNull T apply(@NonNull S state) {
        return action.f(state);
    }
}
