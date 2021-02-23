package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandDefinition;
import perobobbot.plugin.Extension;
import perobobbot.plugin.ExtensionInfo;
import perobobbot.plugin.ExtensionPlugin;

@RequiredArgsConstructor
public abstract class ExtensionFactoryBase<E extends Extension> implements ExtensionPlugin {

    @Getter(AccessLevel.PUBLIC)
    private final @NonNull String extensionName;

    @Override
    public @NonNull ExtensionInfo create(@NonNull Parameters parameters) {
        final var instance = createExtension(parameters);
        final var factory = CommandDefinition.factory(instance.getName());
        final var commandDefinitions = createCommandDefinitions(instance, parameters, factory);
        return new ExtensionInfo(instance, commandDefinitions);
    }

    /**
     * @param parameters the parameters containing some services the extension might use
     * @return a new instance of the extension
     */
    protected abstract @NonNull E createExtension(@NonNull Parameters parameters);

    /**
     * @param extension  the extension the commands will apply to
     * @param parameters this factory parameters containing some services the extension might use
     * @return an optional containing the command bundle that command the extension, an empty optional if the extension has no command
     */
    protected abstract @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull E extension, @NonNull Parameters parameters, @NonNull CommandDefinition.Factory factory);

}
