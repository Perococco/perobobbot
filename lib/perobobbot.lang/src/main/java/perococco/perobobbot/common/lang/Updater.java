package perococco.perobobbot.common.lang;

import lombok.NonNull;

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
