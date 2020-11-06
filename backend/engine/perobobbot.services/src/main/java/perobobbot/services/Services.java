package perobobbot.services;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.services.CachedServices;
import perococco.perobobbot.services.PerococcoServices;
import perococco.perobobbot.services.PerococcoServicesBuilder;

import java.util.Optional;

public interface Services {

    /**
     * @param serviceType the type of the requested service
     * @return an optional containing the service with the requested type, an empty optional if no such service exists
     */
    @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType);

    default boolean hasService(@NonNull Class<?> serviceType) {
        return findService(serviceType).isPresent();
    }

    @NonNull
    default <T> T getService(@NonNull Class<T> serviceType) {
        return findService(serviceType).orElseThrow(() -> new UnknownService(serviceType));
    }

    @NonNull
    default Services filter(@NonNull Requirement requirements) {
        return create(requirements.extractServices(this));
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
