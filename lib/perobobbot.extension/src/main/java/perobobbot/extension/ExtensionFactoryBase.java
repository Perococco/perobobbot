package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import jplugman.api.ServiceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandDefinition;
import perobobbot.plugin.Extension;
import perobobbot.plugin.ExtensionPlugin;

@RequiredArgsConstructor
public abstract class ExtensionFactoryBase<E extends Extension> implements ExtensionFactory {

    @Getter(AccessLevel.PUBLIC)
    private final @NonNull String name;

    @Override
    public @NonNull ExtensionPlugin loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var instance = createExtension(pluginLayer, serviceProvider);
        final var factory = CommandDefinition.factory(instance.getName());
        final var commandDefinitions = createCommandDefinitions(instance, serviceProvider, factory);
        return createExtensionPlugin(instance, commandDefinitions);
    }

    protected abstract @NonNull ExtensionPlugin createExtensionPlugin(@NonNull E extension, @NonNull ImmutableList<CommandDefinition> commandDefinitions);

    /**
     * @param  pluginLayer the module layer used to load the plugin
     * @param serviceProvider the service provider containing the services the extension required
     * @return a new instance of the extension
     */
    protected abstract @NonNull E createExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);

    /**
     * @param extension  the extension the commands will apply to
     * @param serviceProvider the service provider containing the services the extension required
     * @param factory this factory parameters containing some services the extension might use
     * @return an optional containing the command bundle that command the extension, an empty optional if the extension has no command
     */
    protected abstract @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull E extension, @NonNull ServiceProvider serviceProvider, @NonNull CommandDefinition.Factory factory);

}
