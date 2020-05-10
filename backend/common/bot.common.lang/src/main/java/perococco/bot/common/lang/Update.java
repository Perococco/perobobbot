package perococco.bot.common.lang;

import bot.common.lang.Mutation;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.Function0;
import bot.common.lang.fp.Function1;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Bastien Aracil
 * @version 04/07/2018
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
