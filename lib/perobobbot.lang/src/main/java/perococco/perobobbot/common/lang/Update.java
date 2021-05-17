package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Operator;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function0;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class Update<S, R> {

    private final Operator<S, ? extends R> operator;

    @NonNull
    private final Function0<? extends S> rootStateGetter;

    @NonNull
    private final Consumer1<? super S> newRootStateConsumer;

    @NonNull
    public UpdateResult<S, R> performMutation() {
        final S oldState = rootStateGetter.get();
        final S newState = operator.mutate(oldState);

        final UpdateResult<S, R> result = UpdateResult.<S, R>builder()
                .oldRoot(oldState)
                .newRoot(newState)
                .result(operator.getValue(oldState,newState))
                .build();

        newRootStateConsumer.accept(newState);

        return result;
    }
}
