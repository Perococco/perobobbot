package perococco.perobobbot.common.lang;

import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function0;
import perobobbot.common.lang.fp.Function1;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class Update<R,S> {

    @NonNull
    private final Function0<? extends R> rootStateGetter;

    @NonNull
    private final Consumer1<? super R> newRootStateConsumer;

    @NonNull
    @Getter
    private final Mutation<R> mutation;

    @NonNull
    private final Function1<? super R, ? extends S> getter;

    @NonNull
    public UpdateResult<R,S> performMutation() {
        final R currentState = rootStateGetter.get();
        final R newState = mutation.mutate(currentState);

        final UpdateResult<R,S> result = UpdateResult.<R,S>builder()
                .oldRoot(currentState)
                .newRoot(newState)
                .result(getter.apply(newState))
                .build();

        newRootStateConsumer.accept(newState);

        return result;
    }
}
