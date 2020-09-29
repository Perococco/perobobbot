package perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.service.core.CachedServices;
import perococco.perobobbot.service.core.PerococcoServices;
import perococco.perobobbot.service.core.PerococcoServicesBuilder;

import java.util.Optional;

public interface Services {

    @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType);

    default boolean hasService(@NonNull Class<?> serviceType) {
        return findService(serviceType).isPresent();
    }

    @NonNull
    default <T> T getService(@NonNull Class<T> serviceType) {
        return findService(serviceType).orElseThrow(() -> new UnknownService(serviceType));
    }

    @NonNull
    static ServicesBuilder builder() {
        return new PerococcoServicesBuilder();
    }

    @NonNull
    static Services create(@NonNull ImmutableSet<Object> services) {
        return new CachedServices(new PerococcoServices(services));
    }

}
