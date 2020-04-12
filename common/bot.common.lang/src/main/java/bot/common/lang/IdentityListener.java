package bot.common.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface IdentityListener<S> {

    void onValueChange(@NonNull S newValue, @NonNull S oldValue);
}
