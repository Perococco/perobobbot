package perococco.perobobbot.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.services.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
