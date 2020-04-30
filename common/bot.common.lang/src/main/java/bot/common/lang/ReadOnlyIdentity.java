package bot.common.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ReadOnlyIdentity<S> {

    @NonNull
    S getRootState();

    @NonNull
    Subscription addListener(@NonNull IdentityListener<S> listener);

    @NonNull
    Subscription addWeakListener(@NonNull IdentityListener<S> listener);

}
