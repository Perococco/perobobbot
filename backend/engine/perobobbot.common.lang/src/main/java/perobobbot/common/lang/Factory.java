package perobobbot.common.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface Factory<T,R> {

    @NonNull
    T create(@NonNull R parameter);
}
