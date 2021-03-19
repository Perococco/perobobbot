package perobobbot.server.plugin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import jplugman.api.VersionedService;
import jplugman.api.VersionedServiceClass;
import jplugman.api.VersionedServiceProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class BotVersionedServiceProvider implements VersionedServiceProvider {

    private final @NonNull ImmutableList<VersionedService> services;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull VersionedServiceClass<T> versionedServiceClass) {
        return services.stream()
                       .map(versionedServiceClass::castService)
                       .flatMap(Optional::stream)
                       .findFirst();
    }


    public @NonNull Stream<VersionedServiceClass<?>> streamAllServices() {
        return services.stream().map(v -> new VersionedServiceClass<>(v.getType(),v.getVersion()));
    }

}
