package perococco.perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.lang.GetterOnStates;
import perobobbot.lang.Mutation;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function0;

import java.util.concurrent.CompletionStage;

/**
 * @author Perococco
 */
public interface Updater<S> {

    void start();

    void stop();

    @NonNull
    <R> CompletionStage<UpdateResult<S, R>> offerUpdate(
            @NonNull Update<S,R> update
            );


}
