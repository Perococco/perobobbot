package perobobbot.common.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ReadOnlyAsyncIdentity<S> {

    @NonNull
    S getRootState();

    @NonNull
    Subscription addListener(@NonNull IdentityListener<S> listener);

    void addWeakListener(@NonNull IdentityListener<S> listener);

}
