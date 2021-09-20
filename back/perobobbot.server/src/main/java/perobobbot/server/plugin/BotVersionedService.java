package perobobbot.server.plugin;

import jplugman.api.VersionedService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotVersionedService {

    /**
     * The type of the service. <code>service.getClass()</code> might be different (for instance
     * a class implementing this type)
     */
    @Getter
    private final @NonNull Class<?> serviceType;

    /**
     * the service
     */
    private final @NonNull Object service;

    /**
     * the major version of this service. Only the major version
     * is relevant for plugin compatibility
     */
    @Getter
    private final int majorVersion;

    /**
     * If true, this service allow access to sensitive information
     */
    private final boolean sensitive;

    /**
     * Convert this to the jplugman format
     * @return this converted in a jplugman format
     */
    public @NonNull VersionedService toJPlugmanFormat() {
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
