package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function0;
import perococco.perobobbot.common.lang.BasicLazy;
import perococco.perobobbot.common.lang.ThreadSafeLazy;

/**
 * @author perococco
 **/
public interface Lazy<T> {

    @NonNull
    T get();


    static <T> Lazy<T> basic(@NonNull Function0<? extends T> supplier) {
        return new BasicLazy<>(supplier);
    }

    static <T> Lazy<T> threadSafe(@NonNull Function0<? extends T> supplier) {
        return new ThreadSafeLazy<>(supplier);
    }


}
