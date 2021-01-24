package perobobbot.lang;

import lombok.NonNull;

import java.util.function.Predicate;

/**
 * @author Perococco
 */
public interface ObjectPredicate extends Predicate<Object> {

    @Override
    boolean test(@NonNull Object key);
}
