package perococco.perobobbot.service.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.service.core.Services;

import java.util.*;

@RequiredArgsConstructor
public class CachedServices implements Services {

    @NonNull
    private final Services delegate;

    @NonNull
    private final Map<Class<?>,Object> cache = new HashMap<>();

    @Override
    @Synchronized
    public @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType) {
        if (cache.containsKey(serviceType)) {
            return Optional.ofNullable(serviceType.cast(cache.get(serviceType)));
        } else {
            final Optional<T> value = delegate.findService(serviceType);
            cache.put(serviceType,value.orElse(null));
            return value;
        }
    }
}
