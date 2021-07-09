package perobobbot.server.plugin;

import jplugman.api.VersionedService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotVersionedService {

    @Getter
    private final @NonNull Class<?> serviceType;

    private final @NonNull Object service;

    @Getter
    private final int majorVersion;

    private final boolean sensitive;

    public @NonNull VersionedService toVersionedService() {
        final var wrappedService = sensitive?PluginServiceHandler.wrap(service,serviceType):service;
        return new VersionedService(serviceType,wrappedService,majorVersion);
    }

    @Override
    public String toString() {
        return "BotVersionedService{"
                + serviceType.getSimpleName()
                + " v"+majorVersion
                +" : "
                +service.getClass().getSimpleName()
                +" 0x"+Integer.toHexString(System.identityHashCode(service)).toUpperCase()
                +"}";
    }
}
