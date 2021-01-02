package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.command.CommandDefinition;

import java.util.Optional;

public abstract class ExtensionWithoutCommandFactory extends ExtensionFactoryBase<Extension> {

    public ExtensionWithoutCommandFactory(@NonNull String extensionName, @NonNull Parameters parameters) {
        super(extensionName, parameters);
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull Extension extension, @NonNull Parameters parameters) {
        return ImmutableList.of();
    }
}
