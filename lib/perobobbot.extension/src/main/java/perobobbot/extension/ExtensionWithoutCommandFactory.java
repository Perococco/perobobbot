package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.command.CommandDefinition;
import perobobbot.plugin.Extension;

public abstract class ExtensionWithoutCommandFactory extends ExtensionFactoryBase<Extension> {

    public ExtensionWithoutCommandFactory(@NonNull String extensionName) {
        super(extensionName);
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull Extension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        return ImmutableList.of();
    }
}
