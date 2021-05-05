package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Application;
import jplugman.api.PluginService;
import jplugman.api.Version;
import jplugman.api.VersionedService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.Subscription;
import perobobbot.plugin.*;
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
    private final @NonNull ImmutableSet<VersionedService> versionedServices;

    private final @NonNull ExtensionManager extensionManager;
    private final @NonNull WebPluginManager webPluginManager;
    private final @NonNull ChatPlatformPluginManager chatPlatformPluginManager;

    private final Map<PluginService, Subscription> subscriptions = new HashMap<>();

    @Override
    public @NonNull ImmutableSet<VersionedService> getApplicationServices(@NonNull Class<?> pluginExtensionType) {
        return versionedServices;
    }

    @Override
    public void plugService(@NonNull PluginService pluginService) {
        LOG.info("Plug service   : {}",pluginService);
        pluginService.getServiceAs(PerobobbotPlugin.class)
                        .flatMap(p -> p.accept(new PluginVisitor()))
                        .ifPresent(s -> subscriptions.put(pluginService,s));
    }

    @Override
    public void unplugService(@NonNull PluginService pluginService) {
        LOG.info("Unplug service : {}",pluginService);
        final var subscription = subscriptions.remove(pluginService);
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
