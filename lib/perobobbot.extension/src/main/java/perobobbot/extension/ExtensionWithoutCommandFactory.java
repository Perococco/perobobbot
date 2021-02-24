package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.command.CommandDefinition;
import perobobbot.plugin.Extension;
import perobobbot.plugin.ServiceProvider;

public abstract class ExtensionWithoutCommandFactory extends ExtensionFactoryBase<Extension> {

    public ExtensionWithoutCommandFactory(@NonNull String extensionName) {
        super(extensionName);
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull Extension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        return ImmutableList.of();
    }
}
