package perobobbot.common.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ReadOnlyIdentity<S> {

    @NonNull
    S getState();

    @NonNull
    Subscription addListener(@NonNull IdentityListener<S> listener);

    void addWeakListener(@NonNull IdentityListener<S> listener);

}
