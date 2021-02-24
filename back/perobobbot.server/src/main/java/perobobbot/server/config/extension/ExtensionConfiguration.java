package perobobbot.server.config.extension;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.AvailableExtensions;
import perobobbot.extension.ExtensionManager;
import perobobbot.plugin.PluginList;

@Configuration
@Log4j2
public class ExtensionConfiguration {

    private final @NonNull PluginList pluginList;

    private final @NonNull ServiceProviderFactory serviceProviderFactory;

    public ExtensionConfiguration(@NonNull PluginList pluginList, @NonNull IO io, @NonNull ServiceProviderFactory serviceProviderFactory) {
        this.pluginList = pluginList;
        this.serviceProviderFactory = serviceProviderFactory;
        this.serviceProviderFactory.addService(io);
    }

    @Bean
    public AvailableExtensions availableExtensions() {
        final var extensions = ExtensionLister.gatherAllExtensions(pluginList,serviceProviderFactory);
        return new AvailableExtensions(extensions);
    }

    @Bean
    public @NonNull ExtensionManager extensionManager(@NonNull CommandRegistry commandRegistry) {
        return ExtensionManager.create(commandRegistry,availableExtensions());
    }

}
