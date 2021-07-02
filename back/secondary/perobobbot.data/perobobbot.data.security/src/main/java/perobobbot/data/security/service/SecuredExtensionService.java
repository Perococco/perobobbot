package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
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
    public void setExtensionAvailable(@NonNull String extensionName) {
        super.setExtensionAvailable(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void setExtensionUnavailable(@NonNull String extensionName) {
        super.setExtensionUnavailable(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean activateExtension(@NonNull String extensionName) {
        return super.activateExtension(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deactivateExtension(@NonNull String extensionName) {
        return super.deactivateExtension(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'ExtensionEntity','READ')")
    public @NonNull ImmutableList<Extension> listEnabledExtensions(@NonNull UUID botId) {
        return super.listEnabledExtensions(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'ExtensionEntity','READ')")
    public boolean isExtensionEnabled(@NonNull UUID botId, @NonNull String extensionName) {
        return super.isExtensionEnabled(botId, extensionName);
    }

}
