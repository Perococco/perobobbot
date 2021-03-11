package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import jplugman.api.ServiceProvider;
import jplugman.api.Version;
import jplugman.api.VersionedService;
import jplugman.api.VersionedServiceClass;
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
    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var instance = createExtension(serviceProvider);
        final var factory = CommandDefinition.factory(instance.getName());
        final var commandDefinitions = createCommandDefinitions(instance, serviceProvider, factory);
        return createExtensionPlugin(instance, commandDefinitions);
    }

    @Override
    public @NonNull VersionedServiceClass<?> getProvidedServiceClass() {
        return new VersionedServiceClass<>(getExtensionPluginClass(), getPluginVersion());
    }

    protected abstract @NonNull Version getPluginVersion();

    protected abstract @NonNull Class<? extends ExtensionPlugin> getExtensionPluginClass();

    protected abstract @NonNull ExtensionPlugin createExtensionPlugin(@NonNull E extension, @NonNull ImmutableList<CommandDefinition> commandDefinitions);

    /**
     * @param serviceProvider the service provider containing the services the extension required
     * @return a new instance of the extension
     */
    protected abstract @NonNull E createExtension(@NonNull ServiceProvider serviceProvider);

    /**
     * @param extension  the extension the commands will apply to
     * @param serviceProvider the service provider containing the services the extension required
     * @param factory this factory parameters containing some services the extension might use
     * @return an optional containing the command bundle that command the extension, an empty optional if the extension has no command
     */
    protected abstract @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull E extension, @NonNull ServiceProvider serviceProvider, @NonNull CommandDefinition.Factory factory);

}
