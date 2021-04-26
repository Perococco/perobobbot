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

    public @NonNull VersionedService toVersionedService() {
        return new VersionedService(serviceType,service,majorVersion);
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
