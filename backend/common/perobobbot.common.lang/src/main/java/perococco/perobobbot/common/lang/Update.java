package perococco.perobobbot.common.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.GetterOnStates;
import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function0;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class Update<S, R> {

    @NonNull
    private final Function0<? extends S> rootStateGetter;

    @NonNull
    private final Consumer1<? super S> newRootStateConsumer;

    @NonNull
    @Getter
    private final Mutation<S> mutation;

    @NonNull
    private final GetterOnStates<? super S, ? extends R> getter;

    @NonNull
    public UpdateResult<S, R> performMutation() {
        final S currentState = rootStateGetter.get();
        final S newState = mutation.mutate(currentState);

        final UpdateResult<S, R> result = UpdateResult.<S, R>builder()
                .oldRoot(currentState)
                .newRoot(newState)
                .result(getter.getValue(currentState,newState))
                .build();

        newRootStateConsumer.accept(newState);

        return result;
    }
}
