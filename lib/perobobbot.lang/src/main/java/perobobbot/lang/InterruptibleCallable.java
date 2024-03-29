package perobobbot.lang;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface InterruptibleCallable<V> {

    @NonNull
    V call() throws InterruptedException;
}
