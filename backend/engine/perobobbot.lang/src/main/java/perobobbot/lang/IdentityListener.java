package perobobbot.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface IdentityListener<S> {

    void onValueChange(@NonNull S oldValue, @NonNull S newValue);
}
