package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.Extension;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.proxy.ProxyExtensionService;

import java.util.UUID;

@Service
@SecuredService
public class SecuredExtensionService extends ProxyExtensionService {

    public SecuredExtensionService(@NonNull @EventService ExtensionService delegate) {
        super(delegate);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateExtensionList(@NonNull ImmutableSet<String> foundExtensionNames) {
        super.updateExtensionList(foundExtensionNames);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'ExtensionEntity','READ')")
    public @NonNull ImmutableList<Extension> listEnabledExtensions(@NonNull UUID botId) {
        return super.listEnabledExtensions(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'ExtensionEntity','READ')")
    public boolean isExtensionEnabled(@NonNull UUID botId, @NonNull UUID extensionId) {
        return super.isExtensionEnabled(botId, extensionId);
    }
}
