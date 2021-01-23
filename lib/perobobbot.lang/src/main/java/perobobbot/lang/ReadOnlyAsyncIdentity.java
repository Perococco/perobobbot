package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface ReadOnlyAsyncIdentity<S> {

    @NonNull
    CompletionStage<S> getRootState();


    @NonNull
    Subscription addListener(@NonNull IdentityListener<S> listener);

    void addWeakListener(@NonNull IdentityListener<S> listener);

    @NonNull <T> CompletionStage<T> applyToRootState(@NonNull Function1<? super S, ? extends T> getter);

    @NonNull CompletionStage<?> runWithRootState(@NonNull Consumer1<? super S> getter);


}
