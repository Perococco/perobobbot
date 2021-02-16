package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface ReadOnlyAsyncIdentity<S> {

    /**
     * @return a completion stage than will complete after any mutation in progress when
     * calling this method
     */
    @NonNull
    CompletionStage<S> getRootState();

    /**
     * @return the current state which might be out date because of in progress mutations
     */
    @NonNull S getState();

    @NonNull
    Subscription addListener(@NonNull IdentityListener<S> listener);

    void addWeakListener(@NonNull IdentityListener<S> listener);

    @NonNull <T> CompletionStage<T> applyToState(@NonNull Function1<? super S, ? extends T> getter);

    @NonNull CompletionStage<?> runWithState(@NonNull Consumer1<? super S> getter);


}
