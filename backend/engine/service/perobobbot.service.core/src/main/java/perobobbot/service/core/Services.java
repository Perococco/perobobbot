package perobobbot.service.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perococco.perobobbot.service.core.PerococcoServices;
import perococco.perobobbot.service.core.PerococcoServicesBuilder;

import java.util.Optional;

public interface Services {

    @NonNull
    <T> Optional<T> findService(@NonNull Class<T> serviceType);

    @NonNull
    static ServicesBuilder builder() {
        return new PerococcoServicesBuilder();
    }

    @NonNull
    static Services create(@NonNull ImmutableMap<Class<?>,Object> services) {
        return new PerococcoServices(services);
    }
}
