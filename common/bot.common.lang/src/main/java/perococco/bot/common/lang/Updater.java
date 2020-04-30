package perococco.bot.common.lang;

import bot.common.lang.Mutation;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.Function0;
import bot.common.lang.fp.Function1;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 * @version 04/07/2018
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
