package bot.common.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface Lazy<T> {

    @NonNull
    T get();


}
