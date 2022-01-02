package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.BotExtension;
import perobobbot.data.com.Extension;
import perobobbot.data.com.UpdateBotExtensionParameters;
import perobobbot.data.com.UpdateExtensionParameters;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.SecuredService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@SecuredService
public class SecuredExtensionService implements ExtensionService {

    private final @NonNull @EventService ExtensionService delegate;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void setExtensionAvailable(@NonNull String extensionName) {
        delegate.setExtensionAvailable(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void setExtensionUnavailable(@NonNull String extensionName) {
        delegate.setExtensionUnavailable(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean activateExtension(@NonNull String extensionName) {
        return delegate.activateExtension(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deactivateExtension(@NonNull String extensionName) {
        return delegate.deactivateExtension(extensionName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void setAllExtensionAsUnavailable() {
        delegate.setAllExtensionAsUnavailable();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotExtensionEntity','READ')")
    public @NonNull ImmutableList<Extension> listEnabledExtensions(@NonNull UUID botId) {
        return delegate.listEnabledExtensions(botId);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public ImmutableList<Extension> listAvailableExtensions() {
        return delegate.listAvailableExtensions();
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public ImmutableList<Extension> listAllExtensions() {
        return delegate.listAllExtensions();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotExtensionEntity','READ')")
    public boolean isExtensionEnabled(@NonNull UUID botId, @NonNull String extensionName) {
        return delegate.isExtensionEnabled(botId, extensionName);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotExtensionEntity','READ')")
    public ImmutableList<BotExtension> listAllBotExtensions(@NonNull UUID botId) {
        return delegate.listAllBotExtensions(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull Extension updateExtension(@NonNull UUID extensionId, @NonNull UpdateExtensionParameters updateExtensionParameters) {
        return delegate.updateExtension(extensionId,updateExtensionParameters);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull ImmutableList<BotExtension> listAllUserExtensions(@NonNull String login) {
        return delegate.listAllUserExtensions(login);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotExtensionEntity','UPDATE')")
    public BotExtension updateBotExtension(@NonNull UUID botId, @NonNull UUID extensionId, @NonNull UpdateBotExtensionParameters parameters) {
        return delegate.updateBotExtension(botId, extensionId, parameters);
    }
}
