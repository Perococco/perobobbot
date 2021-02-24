package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.ServiceProvider;

import java.util.Optional;

@RequiredArgsConstructor
public class PluginSpecificServiceProvider implements ServiceProvider {

    private final @NonNull ImmutableMap<Class<?>,Object> services;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType) {
        return Optional.ofNullable(services.get(serviceType))
                       .filter(serviceType::isInstance)
                       .map(serviceType::cast);
    }
}
