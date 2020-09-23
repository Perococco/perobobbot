package perococco.perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.GetterOnStates;
import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function0;

import java.util.concurrent.CompletionStage;

/**
 * @author Perococco
 */
public interface Updater<S> {

    void start();

    void stop();

    @NonNull
    <R> CompletionStage<UpdateResult<S, R>> offerUpdatingOperation(
            @NonNull Mutation<S> mutation,
            @NonNull Function0<? extends S> rootStateGetter,
            @NonNull Consumer1<? super S> newRootStateConsumer,
            @NonNull GetterOnStates<? super S, ? extends R> getterOnStates
            );


}
