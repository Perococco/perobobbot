package perococco.perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function0;
import perobobbot.common.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * @author Perococco
 */
public interface Updater<R> {

    void start();

    void stop();

    @NonNull
    <S> CompletionStage<UpdateResult<R,S>> offerUpdatingOperation(
            @NonNull Mutation<R> mutation,
            @NonNull Function0<? extends R> rootStateGetter,
            @NonNull Consumer1<? super R> newRootStateConsumer,
            @NonNull Function1<? super R, ? extends S> subStateGetter
            );


}
