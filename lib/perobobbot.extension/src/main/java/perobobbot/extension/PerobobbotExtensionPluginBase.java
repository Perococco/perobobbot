package perobobbot.extension;

import jplugman.api.ServiceProvider;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.command.CommandDeclaration;
import perobobbot.plugin.Extension;
import perobobbot.plugin.ExtensionPluginData;
import perobobbot.plugin.PerobobbotPlugin;

public abstract class PerobobbotExtensionPluginBase implements PerobobbotPlugin {


    @Getter
    @NonNull ExtensionPluginData data;

    public <E extends Extension> PerobobbotExtensionPluginBase(
            @NonNull ExtensionFactory<E> extensionFactory,
            @NonNull ModuleLayer pluginLayer,
            @NonNull ServiceProvider serviceProvider
    ) {
        final var extension = extensionFactory.createExtension(pluginLayer, serviceProvider);
        final var factory = CommandDeclaration.factory(extension.getName());
        final var commands = extensionFactory.createCommandDefinitions(extension, serviceProvider, factory);
        this.data = new ExtensionPluginData(extension,commands);
    }

    @Override
    public @NonNull String getName() {
        return data.extension().getName();
    }

}
