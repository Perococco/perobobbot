package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.GetterOnStates;
import perobobbot.lang.Mutation;
import perobobbot.lang.Operator;

@RequiredArgsConstructor
public class SimpleOperator<S,T> implements Operator<S,T> {

    private final @NonNull Mutation<S> mutation;

    private final @NonNull GetterOnStates<? super S, ? extends T> action;

    public @NonNull S mutate(S state) {
        return mutation.mutate(state);
    }

    @Override
    public @NonNull T getValue(@NonNull S oldState, @NonNull S newState) {
        return action.getValue(oldState,newState);
    }
}
