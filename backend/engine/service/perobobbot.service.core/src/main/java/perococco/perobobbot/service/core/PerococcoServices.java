package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Services;

import java.util.Optional;

@RequiredArgsConstructor
public class PerococcoServices implements Services {

    @NonNull
    private final ImmutableSet<Object> services;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType) {
        for (Object service : services) {
            if (serviceType.isInstance(service)) {
                return Optional.of(serviceType.cast(service));
            }
        }
        return Optional.empty();
    }

}
