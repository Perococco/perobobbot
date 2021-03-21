package perobobbot.server.plugin;

import jplugman.api.Version;
import jplugman.api.VersionedService;
import jplugman.api.VersionedServiceClass;
import jplugman.api.VersionedServiceProvider;
import jplugman.manager.Application;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.Subscription;
import perobobbot.plugin.ChatPlatformPlugin;
import perobobbot.plugin.ExtensionPlugin;
import perobobbot.plugin.PerobobbotPlugin;
import perobobbot.plugin.WebPlugin;
import perobobbot.server.config.io.ChatPlatformPluginManager;
import perobobbot.server.plugin.webplugin.WebPluginManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class PluginApplication implements Application {

    @Getter
    private final @NonNull Version version;
    @Getter
    private final @NonNull VersionedServiceProvider serviceProvider;

    private final @NonNull ExtensionManager extensionManager;
    private final @NonNull WebPluginManager webPluginManager;
    private final @NonNull ChatPlatformPluginManager chatPlatformPluginManager;

    private final Map<VersionedService, Subscription> subscriptions = new HashMap<>();

    @Override
    public @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceClass<?> pluginServiceType) {
        return serviceProvider;
    }

    @Override
    public void plugService(@NonNull VersionedService versionedService) {
        LOG.info("Plug service   : '{}' '{}'",versionedService.getType().getSimpleName(),versionedService.getVersion());
        versionedService.getServiceAs(PerobobbotPlugin.class)
                        .flatMap(p -> p.accept(new PluginVisitor()))
                        .ifPresent(s -> subscriptions.put(versionedService,s));
    }

    @Override
    public void unplugService(@NonNull VersionedService versionedService) {
        LOG.info("Unplug service : '{}' '{}'",versionedService.getType().getSimpleName(),versionedService.getVersion());
        final var subscription = subscriptions.remove(versionedService);
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    private class PluginVisitor implements PerobobbotPlugin.Visitor<Optional<Subscription>> {
        @Override
        public @NonNull Optional<Subscription> visit(@NonNull ExtensionPlugin extensionPlugin) {
            return extensionManager.addExtension(extensionPlugin);
        }

        @Override
        public @NonNull Optional<Subscription> visit(@NonNull ChatPlatformPlugin chatPlatformPlugin) {
            return chatPlatformPluginManager.addChatPlatformPlugin(chatPlatformPlugin);
        }

        @Override
        public @NonNull Optional<Subscription> visit(@NonNull WebPlugin webPlugin) {
            return Optional.of(webPluginManager.addWebPlugin(webPlugin));
        }
    }

}
