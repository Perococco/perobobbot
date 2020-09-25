package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Services;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class PerococcoServices implements Services {

    @NonNull
    private final ImmutableMap<Class<?>, Object> services;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType) {
        return Optional.ofNullable(services.get(serviceType))
                       .or(() -> anyMatch(serviceType))
                       .map(serviceType::isInstance)
                       .map(serviceType::cast);
    }

    @NonNull
    private Optional<Object> anyMatch(@NonNull Class<?> type) {
        return services.entrySet()
                       .stream()
                       .filter(e -> type.isAssignableFrom(e.getKey()))
                       .map(Map.Entry::getValue)
                       .findFirst();
    }
}
