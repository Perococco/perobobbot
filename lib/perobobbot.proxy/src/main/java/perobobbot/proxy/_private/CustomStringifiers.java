package perobobbot.proxy._private;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author perococco
 */
public class CustomStringifiers {

    private final Map<Class<?>, Entry<?>> stringifiers = new HashMap<>();


    public <T> void addStringifier(Class<T> type, Function<? super T, String> stringifier) {
        stringifiers.put(type, new Entry<>(type, stringifier));
    }

    /**
     * @param object the object to stringify. must not be null
     * @return the object as a string or null if the conversion could not be done
     */
    public String toString(Object object, Function<Object, String> defaultConverter) {
        final String conversion = toString(object);

        if (conversion == null) {
            return defaultConverter.apply(object);
        } else {
            return conversion;
        }
    }

    private <U> String toString(U object) {
        final Entry<?> entry = stringifiers.get(object.getClass());
        if (entry == null) {
            return null;
        }

        final Entry<U> e = entry.cast(object);

        if (e == null) {
            return null;
        }

        return e.stringifier.apply(object);
    }

    @RequiredArgsConstructor
    private static class Entry<T> {

        @NonNull
        private final Class<T> type;

        @NonNull
        private final Function<? super T, String> stringifier;

        public <U> Entry<U> cast(U object) {
            if (type == object.getClass()) {
                return (Entry<U>) this;
            }
            return null;
        }
    }
}
