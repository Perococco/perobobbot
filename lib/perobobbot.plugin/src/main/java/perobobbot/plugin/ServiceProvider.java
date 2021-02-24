package perobobbot.plugin;

import lombok.NonNull;

import java.util.Optional;

public interface ServiceProvider {

    /**
     * @param serviceType the class of the requested service
     * @param <T> the type of the requested service
     * @return an optional containing the service with the requested type, an empty optional
     * if no service with the requested type is available.
     */
    @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType);

    /**
     * @param serviceType the class of the requested service
     * @param <T> the type of the requested service
     * @return the service with the requested type
     * @throws UnknownService if not service with the requested type exists
     */
    default <T> @NonNull T getService(@NonNull Class<T> serviceType) {
        return findService(serviceType).orElseThrow(() -> new UnknownService(serviceType));
    }


}
